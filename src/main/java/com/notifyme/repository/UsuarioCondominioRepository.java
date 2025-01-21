package com.notifyme.repository;

import com.notifyme.persistence.UsuarioCondominio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsuarioCondominioRepository extends JpaRepository<UsuarioCondominio, UUID> {
}
