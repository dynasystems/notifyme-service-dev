package com.notifyme.services;

import com.notifyme.persistence.Notificacao;
import com.notifyme.persistence.Usuario;
import com.notifyme.persistence.enumated.NotificacaoStatusEnum;
import com.notifyme.repository.NotificacaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class NotificacaoService {

    private final NotificacaoRepository repository;

    public void save(Notificacao notificacao) {
        repository.save(notificacao);
    }

    public Notificacao getNotificacao(Usuario usuario, NotificacaoStatusEnum status) {
        return  repository.findByUsuarioAndStatus(usuario, status);
    }
}
