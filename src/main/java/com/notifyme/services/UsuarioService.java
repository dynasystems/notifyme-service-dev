package com.notifyme.services;

import com.notifyme.dto.usuario.*;
import com.notifyme.error.NotifyMeErrorEnum;
import com.notifyme.error.exceptions.CustomException;
import com.notifyme.error.exceptions.UsuarioNotFoundException;
import com.notifyme.persistence.Condominio;
import com.notifyme.persistence.Notificacao;
import com.notifyme.persistence.Usuario;
import com.notifyme.persistence.UsuarioCondominio;
import com.notifyme.persistence.enumated.UserRole;
import com.notifyme.persistence.enumated.UsuarioStatusEnum;
import com.notifyme.repository.UsuarioRepository;
import com.notifyme.utils.PasswordUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordUtils passwordUtils;
    private final NotificacaoService notificacaoService;

    public void save (Usuario usuario) {
        repository.save(usuario);
    }

    public Usuario findById (String id) {
        return  repository.findById(UUID.fromString(id)).orElseThrow(UsuarioNotFoundException::new);
    }

    public Usuario findByEmail (String email) {
        return repository.findByEmail(email).orElseThrow(UsuarioNotFoundException::new);
    }

    public Usuario findByEmailOrTelefoneAndStatus(String userName) {
        return repository.findByTelefoneOrEmailAndStatus(userName, UsuarioStatusEnum.ATIVO).orElseThrow(UsuarioNotFoundException::new);
    }

    @Transactional
    public void newUsuario(Usuario usuario) {

        try {
            validaUsuario(usuario);
            usuario.setPassword(passwordUtils.encode(usuario.getPassword()));
            usuario.setStatus(UsuarioStatusEnum.PENDENTE_DE_VALIDACAO);
            usuario.setRole(UserRole.ADMINCONDOMINIO);
            repository.save(usuario);

            Notificacao notificacao = new Notificacao();
            notificacao.setUsuario(usuario);
            notificacaoService.save(notificacao);

        } catch (Exception e) {
            log.error("Erro ao cadastrar usuario e/ou notificação", e);
            throw e;
        }
    }

    private void validaUsuario(Usuario usuario) {
        var usuarioExistente = repository.findByTelefoneOrEmailOrCpf(usuario.getTelefone(), usuario.getEmail(), usuario.getCpf());

        if (usuarioExistente.isPresent()) {
            if (usuarioExistente.get().getCpf().equals(usuario.getCpf())) {
                throw new CustomException(NotifyMeErrorEnum.CUSTOM_USARIO_EXCEPTION, "CPF");
            }
            if (usuarioExistente.get().getEmail().equals(usuario.getEmail())) {
                throw new CustomException(NotifyMeErrorEnum.CUSTOM_USARIO_EXCEPTION, "e-mail");
            }
            if (usuarioExistente.get().getTelefone().equals(usuario.getTelefone())) {
                throw new CustomException(NotifyMeErrorEnum.CUSTOM_USARIO_EXCEPTION, "telefone");
            }
        }
    }

//    public void updateUsuario (@RequestBody Usuario usuario) {
//
//        try {
//            var usuarioExistente = repository.findByTelefoneOrEmail(usuario.getTelefone(), usuario.getEmail());
//
//            if (usuarioExistente.isPresent()) {
//                throw new UsuarioNotFoundException();
//            }
//            usuario.setPassword(passwordUtils.encode(usuario.getPassword()));
//            usuario.setRole(UserRole.ADMINCONDOMINIO);
//            repository.save(usuario);
//        } catch (Exception e) {
//            log.error("Erro ao cadastrar usuario", e);
//            throw e;
//        }
//    }
}






