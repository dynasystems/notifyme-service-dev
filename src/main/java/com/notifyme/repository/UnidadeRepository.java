package com.notifyme.repository;

import com.notifyme.persistence.Unidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UnidadeRepository extends JpaRepository<Unidade, Integer> {

    Optional<Unidade> findByDescricaoAndCondominioId(String descricao, Integer condominioId);
}
