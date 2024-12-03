package com.notifyme.persistence;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "NM_USUARIO_CONDOMINIO")
public class UsuarioCondominio {
    @Id
    @GeneratedValue(generator = "UUID_generator")
    @Column(name = "ID", columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "USUARIO_ID", referencedColumnName = "ID")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONDOMINIO_ID", referencedColumnName = "ID", updatable = false, nullable = false)
    private Condominio condominio;
}
