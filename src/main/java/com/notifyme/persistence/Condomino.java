package com.notifyme.persistence;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "NM_CONDOMINO")
public class Condomino {

    @Id
    @GeneratedValue(generator = "UUID_generator")
    @Column(name = "ID", columnDefinition = "BINARY(16)")
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UNIDADE_ID", referencedColumnName = "ID", updatable = false, nullable = false)
    private Unidade unidade;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "TELEFONE")
    private String telefone;

    @Column(name = "EMAIL")
    private String email;

}
