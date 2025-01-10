package com.notifyme.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Data
@Entity
@ToString
@Table(name = "NM_CONFIRMATION_TOKEN")
public class ConfirmationToken {

    @Id
    @GeneratedValue(generator = "UUID_generator")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USUARIO_ID", nullable = false)
    private Usuario usuario;

    @Column(name = "CREATE_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now(ZoneOffset.UTC);

    @Column(name = "EXPIRES_AT", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "CONFIRMED_AT", nullable = true)
    private LocalDateTime confirmedAt;

    @Column(name = "TOKEN", columnDefinition = "BINARY(16)", nullable = false, unique = true)
    private UUID token;
}
