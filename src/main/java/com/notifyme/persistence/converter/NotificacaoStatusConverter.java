package com.notifyme.persistence.converter;

import com.notifyme.persistence.enumated.NotificacaoStatusEnum;
import com.notifyme.persistence.enumated.UsuarioStatusEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class NotificacaoStatusConverter implements AttributeConverter<NotificacaoStatusEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(NotificacaoStatusEnum type) {
        return (type != null) ? type.getType() : null;
    }

    @Override
    public NotificacaoStatusEnum convertToEntityAttribute(Integer type) {
        return (type != null) ? NotificacaoStatusEnum.valueOf(type) : null;
    }
}
