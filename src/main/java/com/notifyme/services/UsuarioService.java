package com.notifyme.services;

import com.notifyme.error.NotifyMeErrorEnum;
import com.notifyme.error.exceptions.CustomException;
import com.notifyme.error.exceptions.UsuarioNotFoundException;
import com.notifyme.model.UpdateUsuarioRequestDTO;
import com.notifyme.persistence.ConfirmationToken;
import com.notifyme.persistence.Notificacao;
import com.notifyme.persistence.Usuario;
import com.notifyme.persistence.enumated.UserRole;
import com.notifyme.persistence.enumated.UsuarioStatusEnum;
import com.notifyme.repository.ConfirmationTokenRepository;
import com.notifyme.repository.UsuarioRepository;
import com.notifyme.utils.PasswordUtils;
import com.notifyme.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;


@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioService {

    Logger logger = LogManager.getLogger(UsuarioService.class);

    private final UsuarioRepository repository;
    private final PasswordUtils passwordUtils;
    private final NotificacaoService notificacaoService;
    private final ConfirmationTokenRepository confirmationTokenRepository;

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
    public void novoUsuario(Usuario usuario) {

        try {
            validaUsuario(usuario);
            usuario.setPassword(passwordUtils.encode(usuario.getPassword()));
            usuario.setStatus(UsuarioStatusEnum.PENDENTE_DE_VALIDACAO);
            usuario.setRole(UserRole.ADMINCONDOMINIO);
            Usuario save = repository.save(usuario);

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

    public void updateUsuario (UpdateUsuarioRequestDTO dto) {

        try {

            var usuarioExistente = findById(dto.getId());

            if (nonNull(dto.getNome())) usuarioExistente.setNome(dto.getNome());
            if (nonNull(dto.getTelefone())) usuarioExistente.setTelefone(dto.getTelefone());
            if (nonNull(dto.getEmail())) usuarioExistente.setEmail(dto.getEmail());
            if (nonNull(dto.getPassword())) usuarioExistente.setPassword(passwordUtils.encode(dto.getPassword()));
            if (nonNull(dto.getCpf())) {
                if (Utils.isCPF(dto.getCpf())){
                    usuarioExistente.setCpf(dto.getCpf());
                } else {
                    throw new CustomException(NotifyMeErrorEnum.CPF_USUARIO_INVALIDO, "CPF");
                }
            }
            if (nonNull(dto.getStatus())) usuarioExistente.setStatus(UsuarioStatusEnum.valueOf(dto.getStatus()));
            if (nonNull(dto.getRole())) usuarioExistente.setRole(UserRole.valueOf(dto.getRole()));
            LocalDate agora = LocalDate.now();
            usuarioExistente.setDataAlteracao(agora);

            //repository.save(usuarioExistente);
        } catch (Exception e) {
            log.error("Erro ao editar usuario", e);
            throw e;
        }
    }

    public void deleteUsuario (@PathVariable String id) {
        try {

            var usuarioExistente = findById(id);

            if (isNull(usuarioExistente)) {
                throw new UsuarioNotFoundException();
            }

            usuarioExistente.setStatus(UsuarioStatusEnum.BLOQUEADO);
            LocalDate agora = LocalDate.now();
            usuarioExistente.setDataAlteracao(agora);
            repository.save(usuarioExistente);

        } catch (Exception e) {
            log.error("Erro ao excluir usuario", e);
            throw e;
        }
    }

    public List<Usuario> listaUsuarioStatus(UsuarioStatusEnum status) {
        return  repository.findByStatus(status);
    }
}






