package animalDex.dex.service;

import animalDex.dex.model.Animal;
import animalDex.dex.model.User;
import animalDex.dex.repository.AnimalRepository;
import animalDex.dex.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

@Service
public class AnimalService {

    private static final Logger log = LoggerFactory.getLogger(AnimalService.class);

    private final UserService userService;
    private final AnimalRepository animalRepository;
    private final GeminiService geminiService;
    private final ObjectMapper mapper;
    private final CaptureService captureService;

    public AnimalService(UserService userService, AnimalRepository animalRepository, GeminiService geminiService, ObjectMapper mapper, CaptureService captureService) {
        this.userService = userService;
        this.animalRepository = animalRepository;
        this.geminiService = geminiService;
        this.mapper = mapper;
        this.captureService = captureService;
    }

    public String GetAnimal(byte[] file, String username) throws IOException {
        User user = userService.GetUserByUsername(username);
        if(user == null){
            throw new IllegalArgumentException("User not found");
        }
        //retorna apenas o nome cientifico, nao o json
        String scientificName = geminiService.GetSpeciesName(file);
        if(scientificName == null || scientificName.isBlank()){
            return NoAnimalFoundJSON();
        }
        Animal foundAnimal = CheckAnimalDB(scientificName, user);

        return mapper.writeValueAsString(foundAnimal);
    }

    public Animal CheckAnimalDB(String scientificName, User user) throws IOException{
        Animal foundAnimal = animalRepository.findByScientificName(scientificName);
        if(foundAnimal != null){
            log.info("Animal encontrado no banco: {}", scientificName);
            IncreaseCaches(foundAnimal);
            captureService.CreateCapture(user, foundAnimal, false);
            return foundAnimal;
        }
        log.info("Animal não encontrado no banco, buscando no Gemini: {}", scientificName);
        return CreateAnimalByScientificName(scientificName, user);

    }

    public Animal CreateAnimalByScientificName(String scientificName, User user) throws IOException {
        if (scientificName == null || scientificName.isBlank()) {
            throw new IllegalStateException("Nome vazio, não foi possível criar o animal");
        }
        Animal newAnimal = mapper.readValue(geminiService.GetSpeciesJSON(scientificName), Animal.class);
        newAnimal.setFirstCachedBy(user);
        SaveNewAnimal(newAnimal);
        captureService.CreateCapture(user, newAnimal, true);
        return newAnimal;
    }


    public void SaveNewAnimal(Animal animal){
        try{
            animalRepository.save(animal);
        }
        catch (Exception e){
            System.out.println("erro ao salvar no banco");
        }
    }

    public String NoAnimalFoundJSON(){
        return """
                {
                    "scientificName":"not found"
                }
                """;
    }

    private void IncreaseCaches(Animal animal){
        animal.IncreaseCaches();
        animalRepository.save(animal);
    }

}
