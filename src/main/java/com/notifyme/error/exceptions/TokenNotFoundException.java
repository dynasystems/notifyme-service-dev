package com.notifyme.error.exceptions;

import com.notifyme.error.NotifyMeErrorEnum;

public class TokenNotFoundException extends NotifyMeException {

    public TokenNotFoundException() {
        super(NotifyMeErrorEnum.TOKEN_INVALIDO);
    }
}
