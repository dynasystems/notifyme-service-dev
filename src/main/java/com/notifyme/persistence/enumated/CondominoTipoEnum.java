package com.notifyme.persistence.enumated;

public enum CondominoTipoEnum {
    RESIDENCIAL("R"),
    COMERCIAL("C");

    private String codigo;

    CondominoTipoEnum(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public static CondominoTipoEnum fromCodigo(String codigo) {
        for (CondominoTipoEnum tipo : CondominoTipoEnum.values()) {
            if (tipo.getCodigo().equalsIgnoreCase(codigo)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo não encontrado para o valor: " + codigo);
    }

}
