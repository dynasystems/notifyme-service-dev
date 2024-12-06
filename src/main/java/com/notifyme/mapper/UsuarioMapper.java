package com.notifyme.mapper;

import com.notifyme.model.UsuarioRequestDTO;
import com.notifyme.persistence.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);
    Usuario convert(UsuarioRequestDTO usuarioRequestDTO);

}
