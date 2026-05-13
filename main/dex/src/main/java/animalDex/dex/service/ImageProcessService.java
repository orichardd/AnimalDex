package animalDex.dex.service;

import animalDex.dex.exceptions.ImageProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;

@Service
public class ImageProcessService {

    private Integer maxFileSize = 10 * 1024 * 1024; // colocar como ambient depois *

    private static final int TARGET_BORDERS = 1024;
    private static final float JPEG_QUALITY = 0.85f;

    private static final Set<String> ALLOWED_MIME_TYPES = Set.of(
        "image/jpeg" //front já transforma os outros tipos
    );

    public byte[] ProcessImage(MultipartFile file) throws Exception {
        ValidateFileEmpty(file);
        ValidateFileSize(file);
        ValidateMimeType(file);
        ValidateMagicBytes(file);

        BufferedImage image = ValidateAndReadImage(file); //parte pesada
        return ResizeIfNeeded(image);
    }

    private void ValidateFileEmpty(MultipartFile file) throws ImageProcessingException {
        if(file == null || file.isEmpty()){
            throw new ImageProcessingException("Arquivo vazio", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    private void ValidateFileSize(MultipartFile file) throws ImageProcessingException{
        if(file.getSize() > maxFileSize){
            throw new ImageProcessingException("Arquivo muito grande, tamanho maximo de %d bits".formatted(maxFileSize), HttpStatus.LENGTH_REQUIRED);
        }
    }

    private void ValidateMimeType(MultipartFile file) throws  ImageProcessingException{
        String contentType = file.getContentType();

        if(!(ALLOWED_MIME_TYPES.contains(contentType))){
            throw  new ImageProcessingException("Tipo de arquivo não suportado.", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }
    }

    private static final Map<String, byte[]> MAGIC_BYTES = Map.of(
            "image/jpeg", new byte[]{(byte) 0xFF, (byte) 0xD8, (byte) 0xFF}
    );

    //primeiros 3 bytes do arquivo que realmente dizem que tipo ele é
    private void ValidateMagicBytes(MultipartFile file) throws ImageProcessingException {

        byte[] expectedMagic = MAGIC_BYTES.get(file.getContentType());

        // Defesa extra: se por algum motivo chegou aqui com tipo não mapeado
        if (expectedMagic == null) {
            throw new ImageProcessingException(
                    "Tipo de arquivo não suportado.",
                    HttpStatus.UNSUPPORTED_MEDIA_TYPE
            );
        }

        byte[] headerBytes = new byte[expectedMagic.length];

        try (InputStream is = file.getInputStream()) {
            int bytesRead = is.read(headerBytes);
            if (bytesRead < expectedMagic.length) {
                throw new ImageProcessingException(
                        "Arquivo corrompido ou incompleto.",
                        HttpStatus.UNPROCESSABLE_ENTITY
                );
            }
        } catch (IOException e) {
            throw new ImageProcessingException(
                    "Erro ao ler o arquivo.",
                    HttpStatus.UNPROCESSABLE_ENTITY
            );
        }

        for (int i = 0; i < expectedMagic.length; i++) {
            if (headerBytes[i] != expectedMagic[i]) {
                throw new ImageProcessingException(
                        "O conteúdo do arquivo não corresponde ao tipo declarado.",
                        HttpStatus.UNSUPPORTED_MEDIA_TYPE
                );
            }
        }
    }

    //le a imagem pra ver se nao ta corrompida nem nada
    private BufferedImage ValidateAndReadImage(MultipartFile file) throws ImageProcessingException {
        BufferedImage image;

        try (InputStream is = file.getInputStream()) {
            image = ImageIO.read(is);
        } catch (IOException e) {
            throw new ImageProcessingException("Não foi possível ler a imagem.", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }

        if (image == null) {
            throw new ImageProcessingException("Imagem corrompida ou formato inválido.", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }

        return image;
    }

    private byte[] compressToJpeg(BufferedImage image) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ImageWriter writer = ImageIO.getImageWritersByFormatName("jpeg").next();
        ImageWriteParam params = writer.getDefaultWriteParam();
        params.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        params.setCompressionQuality(JPEG_QUALITY);

        writer.setOutput(ImageIO.createImageOutputStream(outputStream));
        writer.write(null, new IIOImage(image, null, null), params);
        writer.dispose();

        image.flush();
        return outputStream.toByteArray();
    }

    private byte[] ResizeIfNeeded(BufferedImage original) throws IOException {
        int width = original.getWidth();
        int height = original.getHeight();

        // Se já é 1024x1024 só comprime
        if (width == TARGET_BORDERS && height == TARGET_BORDERS) {
            return compressToJpeg(original);
        }

        BufferedImage resized = new BufferedImage(
                TARGET_BORDERS, TARGET_BORDERS, BufferedImage.TYPE_INT_RGB
        );

        Graphics2D g = resized.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_SPEED);

        // Se vier não quadrada, centraliza e corta
        if (width != height) {
            int size = Math.min(width, height); // pega o menor lado
            int x = (width - size) / 2;        // centraliza horizontalmente
            int y = (height - size) / 2;       // centraliza verticalmente

            g.drawImage(original,
                    0, 0, TARGET_BORDERS, TARGET_BORDERS, // destino: 1024x1024
                    x, y, x + size, y + size,         // origem: recorte centralizado
                    null
            );
        } else {
            g.drawImage(original, 0, 0, TARGET_BORDERS, TARGET_BORDERS, null);
        }

        g.dispose();
        original.flush();

        return compressToJpeg(resized);
    }

}
