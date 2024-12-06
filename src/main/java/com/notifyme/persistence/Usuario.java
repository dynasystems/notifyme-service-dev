package com.notifyme.persistence;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.notifyme.dto.login.LoginRequest;
import com.notifyme.persistence.converter.UsuarioStatusConverter;
import com.notifyme.persistence.enumated.UsuarioStatusEnum;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.*;

@Data
@Entity
@Table(name = "NM_USUARIO")
public class Usuario {

    @Id
    @GeneratedValue
    @Column(name = "ID", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "TELEFONE", unique = true)
    private String telefone;

    @Column(name = "EMAIL", unique = true)
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "FOTO")
    private String foto;

    @Column(name = "STATUS")
    @Convert(converter = UsuarioStatusConverter.class)
    private UsuarioStatusEnum status = UsuarioStatusEnum.ATIVO;

    @Column(name = "DATA_CADASTRO")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataCadastro = LocalDate.now();

    @Column(name = "DATA_ALTERACAO")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataAlteracao;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "NM_USUARIO_ROLE", joinColumns = @JoinColumn(name = "ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    private Set<Role> roles;

    public boolean isLoginCorrect(LoginRequest loginRequest, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(loginRequest.password(), this.password);
    }
}
