package com.notifyme.error.exceptions;

import com.notifyme.error.NotifyMeErrorEnum;

public class CredenciaisInvalidException extends NotifyMeException {

    public CredenciaisInvalidException(){
        super(NotifyMeErrorEnum.CREDENCIAIS_INVALIDA);

    }
}
