package com.notifyme.error.exceptions;

import com.notifyme.error.NotifyMeErrorEnum;

public class UsuarioExistenteException  extends NotifyMeException {

    public UsuarioExistenteException(){
        super(NotifyMeErrorEnum.USUARIO_EXISTENTE);

    }
}
