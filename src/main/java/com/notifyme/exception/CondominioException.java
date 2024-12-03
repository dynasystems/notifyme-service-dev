package com.notifyme.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CondominioException extends RuntimeException {

    public CondominioException(String message){
        super(message);
    }

    public CondominioException(String message, Throwable cause){
        super(message, cause);
    }

}
