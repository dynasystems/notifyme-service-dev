package com.notifyme.dto.perfil;

import com.notifyme.dto.condominio.CondominioResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PerfilCompletoResponseDto {

    private PerfilResponseDto perfilResponseDto;

    private List<CondominioResponseDto> condominios;

}
