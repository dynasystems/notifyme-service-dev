package com.notifyme.persistence;

import com.notifyme.persistence.converter.CondominioStatusConverter;
import com.notifyme.persistence.enumated.CondominioStatusEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
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

    @Column(name = "STATUS")
    @Convert(converter = CondominioStatusConverter.class)
    private CondominioStatusEnum status;

    @Column(name = "DATA_CADASTRO")
    private LocalDate dataCadastro;

    @Column(name = "DATA_ALTERACAO")
    private LocalDate dataAlteracao;

    @OneToMany(mappedBy = "condominio", fetch = FetchType.LAZY)
    private List<Unidade> unidades;

}
