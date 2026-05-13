package animalDex.dex.controller;

import animalDex.dex.service.AnimalService;
import animalDex.dex.service.GeminiService;
import animalDex.dex.service.ImageProcessService;
import animalDex.dex.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static animalDex.dex.service.JWTService.GetTokenByHeader;

@RestController
@RequestMapping("/file")
public class FileController {

    private final AnimalService animalService;
    private final JWTService jWTService;
    private final ImageProcessService imageProcessService;

    public FileController(AnimalService animalService, JWTService jWTService, ImageProcessService imageProcessService) {
        this.animalService = animalService;
        this.jWTService = jWTService;
        this.imageProcessService = imageProcessService;
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> Upload(
            @Validated @RequestParam("file")MultipartFile file,
            @RequestHeader("authorization")String header
            ) throws Exception {
        String token = GetTokenByHeader(header);
        String username = jWTService.ExtractUsername(token);
        byte[] image = imageProcessService.ProcessImage(file); //arrumar amanhã, ou fazer retornar multipart file, ou fazer a outra função aceitar byte[]
        String jsonResponse = animalService.GetAnimal(image, username);
        return ResponseEntity.ok(jsonResponse);
    }



}
