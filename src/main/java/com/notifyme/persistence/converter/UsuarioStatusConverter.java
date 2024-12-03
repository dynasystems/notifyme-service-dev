package com.notifyme.persistence.converter;

import com.notifyme.persistence.enumated.UsuarioStatusEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class UsuarioStatusConverter implements AttributeConverter<UsuarioStatusEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(UsuarioStatusEnum situationType) {
        return (situationType != null) ? situationType.getType() : null;
    }

    @Override
    public UsuarioStatusEnum convertToEntityAttribute(Integer type) {
        return (type != null) ? UsuarioStatusEnum.valueOf(type) : null;
    }
}
