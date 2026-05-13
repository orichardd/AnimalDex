package animalDex.dex.exceptions;

import org.springframework.http.HttpStatus;

public class ImageProcessingException extends AnimalDexException{
    public ImageProcessingException(String message, HttpStatus status) {
        super(message, status);
    }
}
