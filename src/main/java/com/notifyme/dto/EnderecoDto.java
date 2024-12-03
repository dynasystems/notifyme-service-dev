package com.notifyme.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class EnderecoDto {

        private String cep;
        private String logradouro;
        private String complemento;
        private String bairro;
        private String localidade;
        private String uf;

}
