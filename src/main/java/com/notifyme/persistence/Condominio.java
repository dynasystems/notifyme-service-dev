package com.notifyme.persistence;

import com.notifyme.persistence.converter.CondominioTipoConverter;
import com.notifyme.persistence.enumated.CondominoTipoEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "NM_CONDOMINIO")
public class Condominio {

    @Id
    @GeneratedValue(generator = "UUID_generator")
    @Column(name = "ID", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "CNPJ")
    private String cnpj;

    @Column(name = "TELEFONE")
    private String telefone;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "CEP")
    private String cep;

    @Column(name = "LOGRADOURO")
    private String logradouro;

    @Column(name = "NUMERO")
    private String numero;

    @Column(name = "COMPLEMENTO")
    private String complemento;

    @Column(name = "BAIRRO")
    private String bairro;

    @Column(name = "CIDADE")
    private String cidade;

    @Column(name = "ESTADO")
    private String estado;

    @Column(name = "DATA_CADASTRO")
    private LocalDateTime dataCadastro = LocalDateTime.now(ZoneOffset.UTC);

    @Column(name = "TIPO")
    @Convert(converter = CondominioTipoConverter.class)
    private CondominoTipoEnum tipo;

    @OneToMany(mappedBy = "condominio", fetch = FetchType.LAZY)
    private List<Unidade> unidades;

}
