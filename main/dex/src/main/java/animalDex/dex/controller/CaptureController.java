package animalDex.dex.controller;

import animalDex.dex.model.Capture;
import animalDex.dex.service.CaptureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/captures")
public class CaptureController {

    @Autowired
    private CaptureService captureService;

    @GetMapping("/dex/{userID}")
    public ResponseEntity<?> GetUserDex(@PathVariable Long userID){
        List<Capture> userDex = captureService.GetUserDex(userID);
        return ResponseEntity.ok(userDex);
    }

}
