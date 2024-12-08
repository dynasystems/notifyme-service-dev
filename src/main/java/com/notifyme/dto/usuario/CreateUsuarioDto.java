package com.notifyme.dto.usuario;

public record CreateUsuarioDto(String nome, String telefone, String email, String password, String ativo, String condominio, String role ) {
}
