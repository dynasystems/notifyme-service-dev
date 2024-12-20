package com.notifyme.error.exceptions;

import com.notifyme.error.NotifyMeErrorEnum;

public class CustomException extends NotifyMeException {

    public CustomException(NotifyMeErrorEnum errorEnum, String paramName) {
        super(errorEnum, paramName);

    }
}
