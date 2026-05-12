package animalDex.dex.service;

import animalDex.dex.model.Animal;
import animalDex.dex.model.Capture;
import animalDex.dex.model.User;
import animalDex.dex.repository.CaptureRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CaptureService {

    private CaptureRepository captureRepository;
    private UserService userService;

    public CaptureService(CaptureRepository captureRepository, UserService userService) {
        this.captureRepository = captureRepository;
        this.userService = userService;
    }

    public void CreateCapture(User user, Animal animal, Boolean isFirstDiscover){
        if(user == null && animal == null){
            throw new IllegalArgumentException("Erro criando captura nova");
        }
        Capture newCapture = new Capture(
                user,
                animal,
                isFirstDiscover
        );
        captureRepository.save(newCapture);
    }

    public List<Capture> GetUserDex(Long userID) {
        User user = userService.GetUserById(userID);
        if(user == null){
            return null;
        }
        return captureRepository.getCapturesByUser(user);
    }
}
