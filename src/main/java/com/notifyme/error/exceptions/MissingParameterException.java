package com.notifyme.error.exceptions;

import com.notifyme.error.NotifyMeErrorEnum;

public class MissingParameterException extends NotifyMeException {

    public MissingParameterException(String message){
        super(NotifyMeErrorEnum.MISSING_REQUIRED_PARAMETER, message);

    }
}
