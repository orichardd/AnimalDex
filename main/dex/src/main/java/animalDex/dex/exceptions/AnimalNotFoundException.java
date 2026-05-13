package animalDex.dex.exceptions;

import org.springframework.http.HttpStatus;

public class AnimalNotFoundException extends AnimalDexException{
    public AnimalNotFoundException(String message, HttpStatus status) {
        super(message, status);
    }
}
