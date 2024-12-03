package com.notifyme.persistence.converter;

import com.notifyme.persistence.enumated.CondominioStatusEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class CondominioStatusConverter implements AttributeConverter<CondominioStatusEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(CondominioStatusEnum situationType) {
        return (situationType != null) ? situationType.getType() : null;
    }

    @Override
    public CondominioStatusEnum convertToEntityAttribute(Integer type) {
        return (type != null) ? CondominioStatusEnum.valueOf(type) : null;
    }
}
