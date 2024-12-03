package com.notifyme.mapper;

import com.notifyme.dto.login.LoginRequest;
import com.notifyme.dto.login.LoginResponse;
import com.notifyme.model.LoginRequestDTO;
import com.notifyme.model.LoginResposeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LoginMapper {
    LoginMapper INSTANCE = Mappers.getMapper(LoginMapper.class);

    LoginRequest convert(LoginRequestDTO loginRequest);
    LoginResposeDTO convertToResponseDTO(LoginResponse loginResponse);
}
