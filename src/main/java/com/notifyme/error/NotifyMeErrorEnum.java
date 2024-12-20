package com.notifyme.error;

public enum NotifyMeErrorEnum {

    MISSING_REQUIRED_PARAMETER("NM-002","Required parameter %s is not present"),
    USUARIO_NAO_ENCONTRADO("NM-003","Usuário não encontrado"),
    CREDENCIAIS_INVALIDA("NM-004","Credenciais inválidas"),
    USUARIO_EXISTENTE("NM-005","Usuário já existente"),
    CUSTOM_USARIO_EXCEPTION("NM-006","O %s já está em uso");

    private String code;
    private String message;

    NotifyMeErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String format(Object... params) {
        return String.format(message, params);
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
