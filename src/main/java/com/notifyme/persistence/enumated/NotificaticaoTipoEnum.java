package com.notifyme.persistence.enumated;

public enum NotificaticaoTipoEnum {
    NOVO_USUARIO(0),
    NOVA_SENHA(1);


    private int status;

    NotificaticaoTipoEnum(int type) {
        this.status = type;
    }

    public int getType() {
        return this.status;
    }

    public static NotificaticaoTipoEnum valueOf(int value) {
        for (NotificaticaoTipoEnum type : NotificaticaoTipoEnum.values()) {
            if (value == type.getType()) {
                return type;
            }
        }
        throw new IllegalArgumentException("Type não encontrado para o valor: " + value);
    }
}
