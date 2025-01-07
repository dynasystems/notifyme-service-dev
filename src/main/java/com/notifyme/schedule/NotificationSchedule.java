package com.notifyme.schedule;

import com.notifyme.persistence.Usuario;
import com.notifyme.persistence.enumated.NotificacaoStatusEnum;
import com.notifyme.persistence.enumated.UsuarioStatusEnum;
import com.notifyme.services.EmailService;
import com.notifyme.services.NotificacaoService;
import com.notifyme.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "notifyme.notification-email.active", havingValue = "true")
public class NotificationSchedule {

    private final UsuarioService usuarioService;
    private final NotificacaoService notificacaoService;
    private final EmailService emailService;


    @Scheduled(timeUnit = TimeUnit.SECONDS, fixedRateString = "${notifyme.notification-email.verification-time-in-seconds}")
    public void enviaEmail(){
        log.info("email sendo enviado");
        List<Usuario> listaUsuarios = usuarioService.listaUsuarioStatus(UsuarioStatusEnum.PENDENTE_DE_VALIDACAO);
        for (Usuario user : listaUsuarios) {
           var notificacao = notificacaoService.getNotificacao(user, NotificacaoStatusEnum.PENDENTE);
           log.info(notificacao.toString());

            Map<String, Object> propMap = new HashMap<>();
            propMap.put("nome", user.getNome());
            propMap.put("mensagem", "Você chegou até aqui!");

            emailService.enviarEmailTemplate("giovani_bueno@live.com", "EMAIL TESTE", propMap);
        }
    }
}
