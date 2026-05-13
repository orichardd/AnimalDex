package animalDex.dex.exceptions;

import org.springframework.http.HttpStatus;

public class AnimalDexException extends RuntimeException{

    private final HttpStatus status;

    public AnimalDexException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() { return status; }

}
