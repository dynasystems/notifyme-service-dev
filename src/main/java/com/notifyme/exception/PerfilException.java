package com.notifyme.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PerfilException extends RuntimeException {

    public PerfilException(String message){
        super(message);
    }

    public PerfilException(String message, Throwable cause){
        super(message, cause);
    }

}
