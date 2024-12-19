package com.notifyme.error;

public enum NotifyMeErrorEnum {

    ERROR_HANDLING("NM-001","Error handling param: %s"),
    MISSING_REQUIRED_PARAMETER("NM-002","Required parameter '%s' is not present"),
    USUARIO_NAO_ENCONTRADO("NM-003","Usuário não encontrado"),
    CREDENCIAIS_INVALIDA("NM-004","Credenciais inválidas");

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
