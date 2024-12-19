package com.notifyme.error.exceptions;

import com.notifyme.error.NotifyMeErrorEnum;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UsuarioNotFoundException extends NotifyMeException {

    public UsuarioNotFoundException(){
        super(NotifyMeErrorEnum.USUARIO_NAO_ENCONTRADO);

    }
}
