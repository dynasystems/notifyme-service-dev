package com.notifyme.error;

public enum NotifyMeErrorEnum {

    MISSING_REQUIRED_PARAMETER("NM-002","Required parameter %s is not present"),
    USUARIO_NAO_ENCONTRADO("NM-003","Usuário não encontrado"),
    CREDENCIAIS_INVALIDA("NM-004","Credenciais inválidas"),
    USUARIO_EXISTENTE("NM-005","Usuário já existente"),
    CUSTOM_USARIO_EXCEPTION("NM-006","O %s já está em uso"),
    CPF_USUARIO_INVALIDO("NM-007","O %s é inválido"),
    ERRO_AO_CARREGAR_TEMPLATE("NM-008","Erro ao carregar template"),
    TOKEN_INVALIDO("NM-009","Token inválido ou expirado."),
    TOKEN_JA_VALIDADO("NM-010","Token já validado.");
    FALHA_AO_CRIAR_DIRETORIO("NM-011"," Falha ao criar diretório de arquivos"),
    ERRO_AO_SALVAR_ARQUIVO("NM-012"," Erro ao salvar Arquivo: %s"),
    ERRO_AO_EXCLUIR_ARQUIVO("NM-013"," Erro ao excluir Arquivo: %s");

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
