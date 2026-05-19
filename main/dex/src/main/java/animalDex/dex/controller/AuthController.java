package animalDex.dex.controller;

import animalDex.dex.DTOs.CreateUserDTO;
import animalDex.dex.DTOs.LoginRequest;
import animalDex.dex.DTOs.LoginResponse;
import animalDex.dex.exceptions.UserAuthentificationException;
import animalDex.dex.model.User;
import animalDex.dex.repository.UserRepository;
import animalDex.dex.service.JWTService;
import animalDex.dex.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static animalDex.dex.service.JWTService.GetTokenByHeader;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/signup")
    public LoginResponse SignUp(@RequestBody CreateUserDTO dto){
        userService.CreateNewUser(dto);
        User user = userService.GetUserByUsername(dto.username());
        String token = jwtService.GenerateToken(user);
        return new LoginResponse(token);
    }

    @PostMapping("/login")
    public LoginResponse Login(@RequestBody LoginRequest request) throws Exception {

        User user = userService.Login(request.username(), request.password());

        String token = jwtService.GenerateToken(user);

        return new LoginResponse(token);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UserAuthentificationException("Token ausente ou inválido", HttpStatus.BAD_REQUEST);
        }

        String token = GetTokenByHeader(authHeader);

        String username;
        try {
            username = jwtService.ExtractUsername(token);
        } catch (ExpiredJwtException e) {
            throw new UserAuthentificationException("Token expirado", HttpStatus.UNAUTHORIZED);
        } catch (JwtException e) {
            throw new UserAuthentificationException("Token inválido", HttpStatus.UNAUTHORIZED);
        }

        User user = userService.GetUserByUsername(username);

        if (!jwtService.IsTokenValid(token, user)) {
            throw new UserAuthentificationException("Token inválido", HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok(Map.of("token", jwtService.GenerateToken(user)));
    }

}
