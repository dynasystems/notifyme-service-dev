package com.notifyme.services;

import com.notifyme.dto.usuario.*;
import com.notifyme.error.exceptions.UsuarioNotFoundException;
import com.notifyme.persistence.Condominio;
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
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final CondominioService condominioService;
    private final PerfilCondominioService perfilCondominioService;
    private final PasswordUtils passwordUtils;

    public void save (Usuario usuario) {
        repository.save(usuario);
    }

    public Page<Usuario> findByFilter (final UsuarioDto filter, Pageable pageable) {
        return repository.findAll(UsuarioRepository.Specs.byFilter(filter), pageable);
    }

    public Usuario findById (String id) {
        return  repository.findById(UUID.fromString(id)).orElseThrow(UsuarioNotFoundException::new);
    }

    public UsuarioCompletoResponseDto findByIdPerfilCompleto (String id) {
        var perfil =  findById(id);
        var perfilCond = perfilCondominioService.findByCondominioPerfil(perfil);
        List<Condominio> condominios = perfilCond.stream().map(pf -> pf.getCondominio()).collect(Collectors.toList());

        var perfilDto = toDto(perfil);
        var condominioDto = condominios.stream().map(c -> condominioService.toCondominioDto(c)).collect(Collectors.toList());

        UsuarioCompletoResponseDto dto = new UsuarioCompletoResponseDto();
        dto.setPerfilResponseDto(perfilDto);
        dto.setCondominios(condominioDto);

        return dto;
    }

    public Usuario findByEmail (String email) {
        Usuario user = repository.findByEmail(email).orElseThrow(UsuarioNotFoundException::new);
        return user;
    }

    public Usuario findByIdPerfiPorTelefoneOrEmailAndStatusS(String filter) {
        log.info("filter " + filter);
        return repository.findPerfilPorTelefoneOrPerfilEmailAndPerfilAtivo(filter, UsuarioStatusEnum.ATIVO)
                .orElseThrow(UsuarioNotFoundException::new);
    }

    public void newUsuario (@RequestBody Usuario usuario) {

        try {
            var usuarioExistente = repository.findByTelefoneOrEmail(usuario.getTelefone(), usuario.getEmail());

            if (usuarioExistente.isPresent()) {
                throw new UsuarioNotFoundException();
            }
            usuario.setPassword(passwordUtils.encode(usuario.getPassword()));
            usuario.setRole(UserRole.ADMINCONDOMINIO);
            repository.save(usuario);
        } catch (Exception e) {
            log.error("Erro ao cadastrar usuario", e);
            throw e;
        }
    }

    public void updateUsuario (@RequestBody Usuario usuario) {

        try {
            var usuarioExistente = repository.findByTelefoneOrEmail(usuario.getTelefone(), usuario.getEmail());

            if (usuarioExistente.isPresent()) {
                throw new UsuarioNotFoundException();
            }
            usuario.setPassword(passwordUtils.encode(usuario.getPassword()));
            usuario.setRole(UserRole.ADMINCONDOMINIO);
            repository.save(usuario);
        } catch (Exception e) {
            log.error("Erro ao cadastrar usuario", e);
            throw e;
        }
    }

    public void newPerfilLogado (CreateUsuarioDto dto, JwtAuthenticationToken token) {
        //pegar usuario logado
        var perfilToken = findById(token.getName());
        //pegar o perfil
        var condominio = perfilCondominioService.findByCondominioPerfil(perfilToken);
        List<Condominio> listCond = condominio.stream().map(UsuarioCondominio::getCondominio).toList();
        log.info("perfilCond  " + listCond);

        var condominioResponse = condominioService.findById(dto.condominio());
        log.info("condominioRequest  " + condominioResponse.getId());


        var perfil = new Usuario();
        perfil.setNome(dto.nome());
        perfil.setTelefone(dto.telefone());
        perfil.setEmail(dto.email());
        perfil.setPassword(passwordUtils.encode(dto.password()));
        perfil.setRole(UserRole.ADMINCONDOMINIO);
        var perfilSalvo = repository.save(perfil);

        UsuarioCondominio pfCondominio = new UsuarioCondominio();
        pfCondominio.setUsuario(perfilSalvo);
        pfCondominio.setCondominio(condominioResponse);
        log.info("pfCondominio " + pfCondominio.toString());

        perfilCondominioService.save(pfCondominio);

    }

    private void verificaPerfilParaCondominio(String condominioId, JwtAuthenticationToken token) {
        //pegar usuario logado
        var perfilToken = findById(token.getName());
        //pegar o perfil
        var condominio = perfilCondominioService.findByCondominioPerfil(perfilToken);
        List<Condominio> listCond = condominio.stream().map(UsuarioCondominio::getCondominio).toList();
        log.info("perfilCond  " + listCond);

        var condominioRequest = condominioService.findById(condominioId);
        log.info("condominioRequest  " + condominioRequest.getId());

    }

    private UsuarioResponseDto toDto(Usuario entity) {
        UsuarioResponseDto dto = new UsuarioResponseDto();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setTelefone(entity.getTelefone());
        dto.setEmail(entity.getEmail());
        dto.setFoto(entity.getFoto());
        dto.setAtivo(entity.getStatus().name());
        dto.setDataCadastro(entity.getDataCadastro());
        dto.setDataAlteracao(entity.getDataAlteracao());
        dto.setRole(entity.getRole());
        return dto;
    }
}






