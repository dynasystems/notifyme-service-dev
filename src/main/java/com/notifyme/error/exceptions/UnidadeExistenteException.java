package com.notifyme.error.exceptions;

import com.notifyme.error.NotifyMeErrorEnum;

public class UnidadeExistenteException extends NotifyMeException{
    public UnidadeExistenteException() {
        super(NotifyMeErrorEnum.UNIDADE_EXISTENTE);
    }
}
