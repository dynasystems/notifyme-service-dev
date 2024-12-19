package com.notifyme.error.exceptions;

import com.notifyme.error.NotifyMeErrorEnum;

public class NotifyMeException extends RuntimeException {

    private final NotifyMeErrorEnum notifyMeErrorTypeEnum;
    private final Object[] params;

    public NotifyMeException(NotifyMeErrorEnum mpError, Object... params) {
        super(mpError.format(params));
        this.notifyMeErrorTypeEnum = mpError;
        this.params = params;
    }

    public String getErrorCode() {
        return notifyMeErrorTypeEnum.getCode();
    }

    public String getErrorDescription() {
        return notifyMeErrorTypeEnum.format(params);
    }
}
