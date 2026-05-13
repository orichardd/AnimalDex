package animalDex.dex.exceptions;

import org.springframework.http.HttpStatus;

public class JSONException extends AnimalDexException {
    public JSONException(String message, HttpStatus status){
        super(message, status);
    }
}
