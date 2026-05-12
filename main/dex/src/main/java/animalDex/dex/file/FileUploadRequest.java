package animalDex.dex.file;

import org.springframework.web.multipart.MultipartFile;

public record FileUploadRequest(
        MultipartFile file
) {
}
