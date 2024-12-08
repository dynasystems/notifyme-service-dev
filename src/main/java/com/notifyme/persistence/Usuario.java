package com.notifyme.persistence;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.notifyme.dto.login.LoginRequest;
import com.notifyme.persistence.converter.UsuarioStatusConverter;
import com.notifyme.persistence.enumated.UserRole;
import com.notifyme.persistence.enumated.UsuarioStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.*;

import static java.util.Objects.nonNull;

@Data
@Entity
@Table(name = "NM_USUARIO")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {

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

    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private UserRole role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRole.ADMINGERAL) return List.of(new SimpleGrantedAuthority("ROLE_ADMINGERAL"),
                                                            new SimpleGrantedAuthority("ROLE_ADMINCONDOMINIO"),
                                                            new SimpleGrantedAuthority("ROLE_USUARIOCONDOMINIO"));
        else if(this.role == UserRole.ADMINCONDOMINIO) return List.of(new SimpleGrantedAuthority("ROLE_ADMINCONDOMINIO"),
                                                                 new SimpleGrantedAuthority("ROLE_USUARIOCONDOMINIO"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USUARIOCONDOMINIO"));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
