package com.notifyme.persistence;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@Table(name = "NM_UNIDADE")
public class Unidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", columnDefinition = "BIGINT")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONDOMINIO_ID", referencedColumnName = "ID", updatable = false, nullable = false)
    private Condominio condominio;

    @Column(name = "DESCRICAO")
    private String descricao;

}
