package com.notifyme.mapper;

import com.notifyme.model.UsuariosResponse;
import com.notifyme.persistence.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UsuariosResponseMapper {

    UsuariosResponseMapper INSTANCE = Mappers.getMapper(UsuariosResponseMapper.class);

    List<UsuariosResponse> convert(List<Usuario> pageRefunds);

    UsuariosResponse convert(Usuario usuario);
}
