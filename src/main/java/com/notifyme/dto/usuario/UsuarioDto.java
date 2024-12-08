package com.notifyme.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UsuarioDto {


    private Long perfilId;

    @NotNull(message = "Nome não pode ser nulo")
    @Size(min = 3, max = 50, message = "Nome deve ter entre 3 e 50 caracteres")
    private String perfilNome;

    @NotNull(message = "telefone não pode ser nulo")
    @Size(min = 10, max = 14, message = "Telefone deve ter entre 10 e 14 caracteres")
    private String perfilTelefone;

    @NotNull(message = "Email não pode ser nulo")
    @Email(message = "Email inválido. Verifique", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String perfilEmail;

    @NotNull(message = "Senha não pode ser nulo")
    @Size(min = 8, max = 20, message = "Senha deve ter entre 8 e 20 caracteres")
    private String perfilPassword;


}
