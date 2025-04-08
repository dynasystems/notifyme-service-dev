package com.notifyme.services;

import com.notifyme.error.exceptions.TokenAlreadyConfirmedException;
import com.notifyme.error.exceptions.TokenNotFoundException;
import com.notifyme.persistence.ConfirmationToken;
import com.notifyme.persistence.Usuario;
import com.notifyme.persistence.enumated.UsuarioStatusEnum;
import com.notifyme.repository.ConfirmationTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioActivationService {

    private final UsuarioService usuarioService;
    private final ConfirmationTokenService confirmationTokenService;

    @Transactional
    public void validaToken(Integer token) {
        log.info("Validando token e ativando usuario, token : {}", token);

        try {
            ConfirmationToken confirmationToken = validarToken(token);
            ativarUsuario(confirmationToken);
            log.info("Token {}, validado com sucesso", token);
        } catch (Exception e) {
            log.error("Erro inesperado ao validar o token: {}", e.getMessage());
            throw e;
        }
    }

    private ConfirmationToken validarToken(Integer token) {
        LocalDateTime currentDateTime = LocalDateTime.now(ZoneOffset.UTC);
        ConfirmationToken confirmationToken = confirmationTokenService.findByTokenAndUnconfirmedAndValid(token, currentDateTime);

        if (confirmationToken.getConfirmedAt() != null) {
            throw new TokenAlreadyConfirmedException();
        }

        confirmationToken.setConfirmedAt(currentDateTime);
        confirmationTokenService.save(confirmationToken);
        return confirmationToken;
    }

    private void ativarUsuario(ConfirmationToken confirmationToken) {
        Usuario usuario = confirmationToken.getUsuario();
        usuario.setStatus(UsuarioStatusEnum.ATIVO);
        usuarioService.save(usuario);
    }
}
