package com.notifyme.services;

import com.notifyme.error.NotifyMeErrorEnum;
import com.notifyme.error.exceptions.CustomException;
import com.notifyme.error.exceptions.UsuarioNotFoundException;
import com.notifyme.mapper.UsuarioMapper;
import com.notifyme.model.NovaSenhaRequestDTO;
import com.notifyme.model.PostUsuarioRedefinirSenhaV1Request;
import com.notifyme.model.UpdateUsuarioRequestDTO;
import com.notifyme.model.UsuarioRequestDTO;
import com.notifyme.models.UsuarioQueryParams;
import com.notifyme.persistence.Condominio;
import com.notifyme.persistence.ConfirmationToken;
import com.notifyme.persistence.Notificacao;
import com.notifyme.persistence.Usuario;
import com.notifyme.persistence.enumated.NotificaticaoTipoEnum;
import com.notifyme.persistence.enumated.UserRole;
import com.notifyme.persistence.enumated.UsuarioStatusEnum;
import com.notifyme.persistence.specifications.UsuarioSpecification;
import com.notifyme.repository.UsuarioRepository;
import com.notifyme.utils.PasswordUtils;
import com.notifyme.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;


@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordUtils passwordUtils;
    private final NotificacaoService notificacaoService;
    private final UploadFileService uploadFileService;
    private final ConfirmationTokenService confirmationTokenService;
    private final CondominioService condominioService;


    public void save (Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public Usuario findById (Integer id) {
        return  usuarioRepository.findById(id).orElseThrow(UsuarioNotFoundException::new);
    }

    public Usuario findByEmail (String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(UsuarioNotFoundException::new);
    }

    public Usuario findByEmailOrTelefoneAndStatus(String userName) {
        return usuarioRepository.findByTelefoneOrEmailAndStatus(userName, UsuarioStatusEnum.ATIVO).orElseThrow(UsuarioNotFoundException::new);
    }

    @Transactional
    public void novoUsuario(UsuarioRequestDTO usuarioRequestDTO) {

        try {
            Usuario newUsuario = UsuarioMapper.INSTANCE.convert(usuarioRequestDTO);

            validaUsuario(newUsuario);

            newUsuario.setPassword(passwordUtils.encode(newUsuario.getPassword()));
            newUsuario.setStatus(UsuarioStatusEnum.PENDENTE_DE_VALIDACAO);
            newUsuario.setRole(UserRole.ADMINCONDOMINIO);

            Usuario usuarioSalvo = usuarioRepository.save(newUsuario);

            createCondominio(usuarioRequestDTO, usuarioSalvo);

            createNotificacao(usuarioSalvo, NotificaticaoTipoEnum.NOVO_USUARIO);

        } catch (Exception e) {
            log.error("Erro ao cadastrar usuario e/ou notificação", e);
            throw e;
        }
    }

    private void createCondominio(UsuarioRequestDTO usuarioRequestDTO, Usuario usuarioSalvo) {
        Condominio newCondominio = new Condominio();
        newCondominio.setNome(usuarioRequestDTO.getCondominio().getRazao());
        newCondominio.setCnpj(usuarioRequestDTO.getCondominio().getCnpj());
        condominioService.newCondominio(usuarioSalvo, newCondominio);
    }


    private void validaUsuario(Usuario usuario) {
        var usuarioExistente = usuarioRepository.findByTelefoneOrEmailOrCpf(usuario.getTelefone(), usuario.getEmail(), usuario.getCpf());

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

    public void updateUsuario (@PathVariable Integer id, @RequestBody UpdateUsuarioRequestDTO dto) {
        try {
            var usuarioExistente = findById(id);

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
            usuarioRepository.save(usuarioExistente);
        } catch (Exception e) {
            log.error("Erro ao editar usuario", e);
            throw e;
        }
    }

    public void uploadFotoPerfil(@PathVariable Integer id, @RequestBody MultipartFile file) {
        try {
            var usuarioExistente = findById(id);

            usuarioExistente.setDataAlteracao(LocalDate.now());
            String url =  uploadFileService.uploadFile(usuarioExistente.getCpf(), file);
            usuarioExistente.setFoto(url);
            usuarioRepository.save(usuarioExistente);

        } catch (Exception e) {
            log.error("Erro ao salvar foto do Usuário", e);
            throw e;
        }
    }

    public void deleteUsuario (@PathVariable Integer id) {
        try {

            var usuarioExistente = findById(id);

            if (isNull(usuarioExistente)) {
                throw new UsuarioNotFoundException();
            }

            usuarioExistente.setStatus(UsuarioStatusEnum.BLOQUEADO);
            LocalDate agora = LocalDate.now();
            usuarioExistente.setDataAlteracao(agora);
            usuarioRepository.save(usuarioExistente);

        } catch (Exception e) {
            log.error("Erro ao excluir usuario", e);
            throw e;
        }
    }

    public List<Usuario> listaUsuarioStatus(UsuarioStatusEnum status) {
        return  usuarioRepository.findByStatus(status);
    }

    public void redefinirSenha(PostUsuarioRedefinirSenhaV1Request postUsuarioRedefinirSenhaV1Request) {
        try {
            Usuario usuario = usuarioRepository.findByTelefoneOrEmailAndStatus(postUsuarioRedefinirSenhaV1Request.getContato(),
                    UsuarioStatusEnum.ATIVO).orElseThrow(UsuarioNotFoundException::new);
            createNotificacao(usuario, NotificaticaoTipoEnum.NOVA_SENHA);
        } catch (Exception e) {
        log.error("Erro ao redefinir nova senha do usuario", e);
        throw e;
        }
    }

    private void createNotificacao(Usuario save, NotificaticaoTipoEnum novoUsuario) {
        Notificacao notificacao = new Notificacao();
        notificacao.setUsuario(save);
        notificacao.setTipo(novoUsuario);
        notificacaoService.save(notificacao);
    }

    @Transactional
    public void novaSenha(Integer id, NovaSenhaRequestDTO novaSenhaRequestDTO) {
        log.info("Atualizando a senha do usuario id {}", id);
        try {
            Usuario usuario = findById(id);
            LocalDateTime currentDateTime = LocalDateTime.now(ZoneOffset.UTC);
            ConfirmationToken token = confirmationTokenService.findByTokenAndUnconfirmedAndValid(novaSenhaRequestDTO.getToken(), currentDateTime);
            token.setConfirmedAt(currentDateTime);
            confirmationTokenService.save(token);

            usuario.setPassword(passwordUtils.encode(novaSenhaRequestDTO.getSenha()));
            usuarioRepository.save(usuario);
            log.info("Senha do usuario id {} atualizada com sucesso", id);
        }catch (Exception e) {
            log.error("Erro ao atualizar a nova senha do usuario", e);
            throw e;
        }
    }

    public Page<Usuario> findByAllPaged(UsuarioQueryParams usuarioQueryParams) {
        final Pageable pageable = PageRequest.of(usuarioQueryParams.getPageNumber(),usuarioQueryParams.getPageSize(), Sort.by(Sort.Direction.DESC,"nome"));

        Specification<Usuario> spec  = UsuarioSpecification.filtrarUsuarios(usuarioQueryParams.getCondominioId(), usuarioQueryParams.getNome(),usuarioQueryParams.getCpf(),
                usuarioQueryParams.getTelefone(), usuarioQueryParams.getEmail(),usuarioQueryParams.getUsuarioStatusEnum());

        return usuarioRepository.findAll(spec, pageable);

    }
}






