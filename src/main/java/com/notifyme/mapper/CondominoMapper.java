package com.notifyme.mapper;

import com.notifyme.model.CondominoRequestDTO;
import com.notifyme.persistence.Condomino;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CondominoMapper {

    CondominoMapper INSTANCE = Mappers.getMapper(CondominoMapper.class);
    Condomino convert(CondominoRequestDTO condominoRequestDTO);
}
