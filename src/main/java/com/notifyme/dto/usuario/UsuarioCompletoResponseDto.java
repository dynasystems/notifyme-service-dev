package com.notifyme.dto.usuario;

import com.notifyme.dto.condominio.CondominioResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UsuarioCompletoResponseDto {

    private UsuarioResponseDto perfilResponseDto;

    private List<CondominioResponseDto> condominios;

}
