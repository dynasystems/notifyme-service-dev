package com.notifyme.mapper;

import com.notifyme.model.CondominioRequestDTO;
import com.notifyme.persistence.Condominio;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CondominioMapper {
    CondominioMapper INSTANCE = Mappers.getMapper(CondominioMapper.class);
    Condominio convert(CondominioRequestDTO condominioRequestDTO);
}
