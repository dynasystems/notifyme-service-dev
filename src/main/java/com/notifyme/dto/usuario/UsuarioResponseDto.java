package com.notifyme.dto.usuario;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.notifyme.persistence.enumated.UserRole;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class UsuarioResponseDto {

    private UUID id;

    private String nome;

    private String telefone;

    private String email;

    private String foto;

    private String ativo;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataCadastro;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataAlteracao;

    private UserRole role;

}
