package com.notifyme.repository;

import com.notifyme.persistence.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken,UUID> {

    @Query("SELECT t FROM ConfirmationToken t WHERE t.token = :token AND t.confirmedAt IS NULL AND t.expiresAt > :currentDateTime")
    Optional<ConfirmationToken> findByTokenAndUnconfirmedAndValid(@Param("token") UUID token, @Param("currentDateTime") LocalDateTime currentDateTime);
}
