package com.notifyme.mapper;

import com.notifyme.dto.login.LoginResponse;
import com.notifyme.model.LoginResposeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LoginMapper {
    LoginMapper INSTANCE = Mappers.getMapper(LoginMapper.class);
    LoginResposeDTO convertToResponseDTO(LoginResponse loginResponse);
}
