package com.notifyme.repository;

import com.notifyme.persistence.Usuario;
import com.notifyme.persistence.UsuarioCondominio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PerfilCondominioRepository extends JpaRepository<UsuarioCondominio, UUID> {

    List<UsuarioCondominio> findByUsuario(Usuario perfil);
    List<UsuarioCondominio> findByUsuarioId(UUID id);

}
