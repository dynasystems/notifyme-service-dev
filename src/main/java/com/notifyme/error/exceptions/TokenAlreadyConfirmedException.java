package com.notifyme.error.exceptions;

import com.notifyme.error.NotifyMeErrorEnum;

public class TokenAlreadyConfirmedException extends NotifyMeException {

    public TokenAlreadyConfirmedException() {
        super(NotifyMeErrorEnum.TOKEN_JA_VALIDADO);
    }
}
