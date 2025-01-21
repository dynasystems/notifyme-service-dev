package com.notifyme.error.exceptions;

import com.notifyme.error.NotifyMeErrorEnum;

public class CondominioExistenteException extends NotifyMeException{

    public CondominioExistenteException() {
        super(NotifyMeErrorEnum.CONDOMINIO_EXISTENTE);
    }
}
