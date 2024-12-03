package com.notifyme.dto.condominio;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CondominioResponseDto {

    private String condominioId;

    private String condominioNome;

    private String condominioCnpj;

    private String condominioTelefone;

    private String condominioEmail;

    private String condominioCep;

    private String condominioLogradouro;

    private String condominioNumero;

    private String condominioComplemento;

    private String condominioBairro;

    private String condominioCidade;

    private String condominioEstado;

    private String condominioAtivo;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate comdominioDataCadastro;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate condominioDataAlteracao;


}
