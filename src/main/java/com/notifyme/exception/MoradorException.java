package com.notifyme.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MoradorException extends RuntimeException {

    public MoradorException(String message){
        super(message);
    }

    public MoradorException(String message, Throwable cause){
        super(message, cause);
    }

}
