package com.notifyme.error.exceptions;

import com.notifyme.error.NotifyMeErrorEnum;

public class NotifyMeException extends RuntimeException {

    private final NotifyMeErrorEnum notifyMeErrorTypeEnum;
    private final Object[] params;

    public NotifyMeException(NotifyMeErrorEnum notifyMeErrorTypeEnum, Object... params) {
        super(notifyMeErrorTypeEnum.format(params));
        this.notifyMeErrorTypeEnum = notifyMeErrorTypeEnum;
        this.params = params;
    }

    public String getErrorCode() {
        return notifyMeErrorTypeEnum.getCode();
    }

    public String getErrorDescription() {
        return notifyMeErrorTypeEnum.format(params);
    }
}
