package com.notifyme.error.exceptions;

import com.notifyme.error.NotifyMeErrorEnum;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class VelocityTemplateException extends NotifyMeException {

    public VelocityTemplateException(){
        super(NotifyMeErrorEnum.ERRO_AO_CARREGAR_TEMPLATE);
    }

}
