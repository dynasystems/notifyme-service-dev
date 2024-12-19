package com.notifyme.error.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MovimentacaoException extends RuntimeException {

    public MovimentacaoException(String message){
        super(message);
    }

    public MovimentacaoException(String message, Throwable cause){
        super(message, cause);
    }

}
