package animalDex.dex.exceptions;

import org.springframework.http.HttpStatus;

public class AIResponseException extends AnimalDexException {
    public AIResponseException(String message, HttpStatus status) {
        super(message, status);
    }
}
