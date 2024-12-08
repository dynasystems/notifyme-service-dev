package com.notifyme.dto.usuario;

import com.notifyme.persistence.enumated.UserRole;

public record UsuarioResponse(Long perfilId,
                              String perfilNome,
                              String PerfilEmail,
                              String perfiFoto,
                              String PerfilAtivo,
                              UserRole role) {
}
