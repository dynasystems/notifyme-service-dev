package com.notifyme.dto.perfil;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.notifyme.persistence.Role;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Data
public class PerfilResponseDto {

    private UUID perfilId;

    private String perfilNome;

    private String perfilTelefone;

    private String perfilEmail;

    private String perfilFoto;

    private String perfilAtivo;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate perfilDataCadastro;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate perfilDataAlteracao;

    private Set<Role> roles;

}
