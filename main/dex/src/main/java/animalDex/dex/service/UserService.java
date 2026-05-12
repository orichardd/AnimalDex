package animalDex.dex.service;

import animalDex.dex.DTOs.CreateUserDTO;
import animalDex.dex.model.User;
import animalDex.dex.repository.UserRepository;
import animalDex.dex.security.BcryptManager;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final BcryptManager bcryptManager;

    private final UserRepository userRepository;

    public UserService(BcryptManager bcryptManager, UserRepository userRepository) {
        this.bcryptManager = bcryptManager;
        this.userRepository = userRepository;
    }



    public void CreateNewUser(CreateUserDTO dto) {
        if(dto.username().length() < 4){
            throw new IllegalArgumentException("Username must have more than 4 characters");
        }
        if(dto.password().length() < 8){
            throw new IllegalArgumentException("Password is too weak, must have at least 8 characters");
        }
        if(!dto.username().matches("^[a-zA-Z0-9_.]+$")) {
            throw new IllegalArgumentException("Username has invalid characters");
        }
        User foundUser = userRepository.getUserByUsername(dto.username());
        if(foundUser != null){
            throw new IllegalArgumentException("Username already taken");
        }
        String hashed = bcryptManager.EncodePassword(dto.password());
        User newUser = new User(
                dto.username(),
                hashed,
                dto.picNum()
        );
        userRepository.save(newUser);

    }

    public User GetUserByUsername(String username){
        return userRepository.getUserByUsername(username);
    }

    public User GetUserById(Long userID) {
        return userRepository.getUserById(userID);
    }
}
