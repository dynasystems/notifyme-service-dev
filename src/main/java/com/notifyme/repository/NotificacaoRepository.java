package com.notifyme.repository;

import com.notifyme.persistence.Notificacao;
import com.notifyme.persistence.Usuario;
import com.notifyme.persistence.enumated.NotificacaoStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface NotificacaoRepository extends JpaRepository<Notificacao, UUID>, JpaSpecificationExecutor<Notificacao> {

    Notificacao findByUsuarioAndStatus(Usuario usuario, NotificacaoStatusEnum status);
}
