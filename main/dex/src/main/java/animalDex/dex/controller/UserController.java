package animalDex.dex.controller;

import animalDex.dex.DTOs.CreateUserDTO;
import animalDex.dex.model.Capture;
import animalDex.dex.repository.CaptureRepository;
import animalDex.dex.repository.UserRepository;
import animalDex.dex.service.JWTService;
import animalDex.dex.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static animalDex.dex.service.JWTService.GetTokenByHeader;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final JWTService jwtService;
    private final CaptureRepository captureRepository;

    public UserController(UserService userService, JWTService jwtService, CaptureRepository captureRepository) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.captureRepository = captureRepository;
    }

    @GetMapping("/dex/get")
    public ResponseEntity<?> GetDex(@RequestHeader("Authorization") String authHeader){
        String token = GetTokenByHeader(authHeader);
        String username = jwtService.ExtractUsername(token);
        List<Capture> captures = captureRepository.getCapturesByUser(userService.GetUserByUsername(username));

        return ResponseEntity.ok(captures);
    }
}
