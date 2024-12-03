package com.notifyme.dto.condominio;

public record CondominioResponse(String condominioId,
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
                                 String condominiEstado,
                                 String condominioAtivo) {
}
