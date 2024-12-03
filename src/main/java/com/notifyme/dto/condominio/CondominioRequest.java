package com.notifyme.dto.condominio;

public record CondominioRequest(Long condominioId,
                                String condominioNome,
                                String condominioCnpj,
                                String condominioTelefone,
                                String condominioEmail,
                                String condominioCep,
                                String condominioLogradouro,
                                String condominioNumero,
                                String condominioComplemento,
                                String condominioBairro,
                                String condominioCidade,
                                String condominioEstado) {

}
