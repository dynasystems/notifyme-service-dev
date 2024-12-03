package com.notifyme.dto.perfil;

import com.notifyme.persistence.Role;

import java.util.Set;

public record PerfilResponse(Long perfilId,
                             String perfilNome,
                             String PerfilEmail,
                             String perfiFoto,
                             String PerfilAtivo,
                             Set<Role> roles) {
}
