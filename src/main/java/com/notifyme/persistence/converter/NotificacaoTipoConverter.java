package com.notifyme.persistence.converter;

import com.notifyme.persistence.enumated.NotificaticaoTipoEnum;
import jakarta.persistence.AttributeConverter;

public class NotificacaoTipoConverter implements AttributeConverter<NotificaticaoTipoEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(NotificaticaoTipoEnum type) {
        return (type != null) ? type.getType() : null;
    }

    @Override
    public NotificaticaoTipoEnum convertToEntityAttribute(Integer type) {
        return (type != null) ? NotificaticaoTipoEnum.valueOf(type) : null;
    }
}
