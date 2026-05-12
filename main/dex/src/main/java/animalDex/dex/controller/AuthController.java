package animalDex.dex.controller;

import animalDex.dex.DTOs.CreateUserDTO;
import animalDex.dex.DTOs.LoginRequest;
import animalDex.dex.DTOs.LoginResponse;
import animalDex.dex.model.User;
import animalDex.dex.repository.UserRepository;
import animalDex.dex.service.JWTService;
import animalDex.dex.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public LoginResponse SignUp(@RequestBody CreateUserDTO dto){
        userService.CreateNewUser(dto);
        User user = userService.GetUserByUsername(dto.username());
        String token = jwtService.GenerateToken(user);
        return new LoginResponse(token);
    }

    @PostMapping("/login")
    public LoginResponse Login(@RequestBody LoginRequest request){

        User user = userService.GetUserByUsername(request.username());

        String token = jwtService.GenerateToken(user);

        return new LoginResponse(token);
    }

}
