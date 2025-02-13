package com.notifyme.error.exceptions;

import com.notifyme.error.NotifyMeErrorEnum;

public class UnidadeNotFoundException extends NotifyMeException{

    public UnidadeNotFoundException() {
        super(NotifyMeErrorEnum.UNIDADE_NAO_ENCONTRADA);
    }
}
