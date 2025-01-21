package com.notifyme.persistence.converter;

import com.notifyme.persistence.enumated.CondominoTipoEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class CondominioTipoConverter implements AttributeConverter<CondominoTipoEnum, String> {

    @Override
    public String convertToDatabaseColumn(CondominoTipoEnum condominoTipoEnum) {
        return (condominoTipoEnum != null) ? condominoTipoEnum.getCodigo() : null;
    }

    @Override
    public CondominoTipoEnum convertToEntityAttribute(String tipo) {
        return (tipo != null) ? CondominoTipoEnum.fromCodigo(tipo) : null;
    }
}
