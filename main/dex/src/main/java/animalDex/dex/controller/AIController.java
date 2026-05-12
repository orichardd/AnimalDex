package animalDex.dex.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/ai")
public class AIController {

    @RequestMapping("/animal")
    public ResponseEntity<?> GetAnimal(){
        return ResponseEntity.ok().body("Ok");
    }



}
