package com.notifyme.persistence.enumated;

import jakarta.persistence.Converter;

public enum CondominioStatusEnum {
    INATIVO(0),
    ATIVO(1);

    private int status;

    CondominioStatusEnum(int status) {
        this.status = status;
    }

    public int getType() {
        return this.status;
    }

    public static CondominioStatusEnum valueOf(int value) {
        for (CondominioStatusEnum type : CondominioStatusEnum.values()) {
            if (value == type.getType()) {
                return type;
            }
        }
        throw new IllegalArgumentException("Status não encontrado para o valor: " + value);
    }
}
