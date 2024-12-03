package com.notifyme.persistence.enumated;

public enum UsuarioStatusEnum {
    INATIVO(0),
    ATIVO(1);

    private int status;

    UsuarioStatusEnum(int status) {
        this.status = status;
    }

    public int getType() {
        return this.status;
    }

    public static UsuarioStatusEnum valueOf(int value) {
        for (UsuarioStatusEnum type : UsuarioStatusEnum.values()) {
            if (value == type.getType()) {
                return type;
            }
        }
        throw new IllegalArgumentException("Status não encontrado para o valor: " + value);
    }
}
