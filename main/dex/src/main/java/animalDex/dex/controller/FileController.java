package animalDex.dex.controller;

import animalDex.dex.service.AnimalService;
import animalDex.dex.service.GeminiService;
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

    public FileController(AnimalService animalService, JWTService jWTService) {
        this.animalService = animalService;
        this.jWTService = jWTService;
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> Upload(
            @Validated @RequestParam("file")MultipartFile file,
            @RequestHeader("authorization")String header
            ) throws IOException {
        String token = GetTokenByHeader(header);
        String username = jWTService.ExtractUsername(token);
        String jsonResponse = animalService.GetAnimal(file, username);
        return ResponseEntity.ok(jsonResponse);
    }



}
