package com.notifyme.error.exceptions;

import com.notifyme.error.NotifyMeErrorEnum;

public class CondominoExistenteNaUnidadeException extends NotifyMeException{

    public CondominoExistenteNaUnidadeException() {
        super(NotifyMeErrorEnum.CONDOMINO_EXISTENTE_UNIDADE);
    }
}
