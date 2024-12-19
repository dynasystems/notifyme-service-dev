package com.notifyme.error.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvoiceException extends RuntimeException {

    public InvoiceException(String message){
        super(message);
    }

    public InvoiceException(String message, Throwable cause){
        super(message, cause);
    }

}
