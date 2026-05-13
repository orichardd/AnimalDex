package animalDex.dex.exceptions;

import org.springframework.http.HttpStatus;

public class UserAuthentificationException extends AnimalDexException{

    public UserAuthentificationException(String message, HttpStatus status) {
        super(message, status);
    }
}
