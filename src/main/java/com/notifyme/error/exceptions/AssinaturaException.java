package com.notifyme.error.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AssinaturaException extends RuntimeException {

    public AssinaturaException(String message){
        super(message);
    }

    public AssinaturaException(String message, Throwable cause){
        super(message, cause);
    }

}
