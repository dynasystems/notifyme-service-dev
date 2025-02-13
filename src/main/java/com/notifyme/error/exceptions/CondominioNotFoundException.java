package com.notifyme.error.exceptions;

import com.notifyme.error.NotifyMeErrorEnum;

public class CondominioNotFoundException extends NotifyMeException{
    public CondominioNotFoundException() {
        super(NotifyMeErrorEnum.CONDOMINIO_NAO_ENCONTRADO);
    }
}
