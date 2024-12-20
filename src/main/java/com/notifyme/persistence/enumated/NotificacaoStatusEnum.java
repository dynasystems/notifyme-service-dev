package com.notifyme.persistence.enumated;

public enum NotificacaoStatusEnum {
    PENDENTE(0),
    ENVIADO(1),
    FALHA(2);

    private int status;

    NotificacaoStatusEnum(int status) {
        this.status = status;
    }

    public int getType() {
        return this.status;
    }

    public static NotificacaoStatusEnum valueOf(int value) {
        for (NotificacaoStatusEnum type : NotificacaoStatusEnum.values()) {
            if (value == type.getType()) {
                return type;
            }
        }
        throw new IllegalArgumentException("Status não encontrado para o valor: " + value);
    }
}
