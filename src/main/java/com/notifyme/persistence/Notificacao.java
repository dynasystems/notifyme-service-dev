package com.notifyme.persistence;

import com.notifyme.persistence.converter.NotificacaoStatusConverter;
import com.notifyme.persistence.converter.NotificacaoTipoConverter;
import com.notifyme.persistence.enumated.NotificacaoStatusEnum;
import com.notifyme.persistence.enumated.NotificaticaoTipoEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Data
@Entity
@ToString
@Table(name = "NM_NOTIFICACAO")
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", columnDefinition = "BIGINT")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USUARIO_ID", referencedColumnName = "ID", nullable = false)
    private Usuario usuario;

    @Column(name = "STATUS")
    @Convert(converter = NotificacaoStatusConverter.class)
    private NotificacaoStatusEnum status = NotificacaoStatusEnum.PENDENTE;

    @Column(name = "DATA_CADASTRO")
    private LocalDateTime dataCadastro = LocalDateTime.now(ZoneOffset.UTC);

    @Column(name = "DATA_ENVIO")
    private LocalDateTime dataEnvio;

    @Column(name = "TENTATIVAS", unique = true)
    private Integer tentativas;

    @Column(name = "TIPO")
    @Convert(converter = NotificacaoTipoConverter.class)
    private NotificaticaoTipoEnum tipo;

}
