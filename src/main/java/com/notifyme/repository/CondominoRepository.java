package com.notifyme.repository;

import com.notifyme.persistence.Condomino;
import com.notifyme.persistence.Unidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CondominoRepository extends JpaRepository<Condomino, UUID> {

    @Query("SELECT c FROM Condomino c WHERE c.unidade = :unidade")
    List<Condomino> getAllCondominoByUnidade(@Param("unidade") Unidade unidade);
}
