package com.notifyme.repository;

import com.notifyme.persistence.Condominio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CondominioRepository extends JpaRepository<Condominio, UUID> {

    Optional<Condominio> findByNome(String nome);
    Optional<Condominio> findByCnpj(String cnpj);
}