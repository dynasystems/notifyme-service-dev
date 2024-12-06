package com.notifyme.services;

import com.notifyme.dto.perfil.*;
import com.notifyme.exception.PerfilException;
import com.notifyme.persistence.Condominio;
import com.notifyme.persistence.Usuario;
import com.notifyme.persistence.UsuarioCondominio;
import com.notifyme.persistence.Role;
import com.notifyme.persistence.enumated.UsuarioStatusEnum;
import com.notifyme.repository.UsuarioRepository;
import com.notifyme.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final CondominioService condominioService;
    private final PerfilCondominioService perfilCondominioService;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public Page<Usuario> findByFilter (final PerfilDto filter, Pageable pageable) {
        return repository.findAll(UsuarioRepository.Specs.byFilter(filter), pageable);

    }

    public Usuario findById (String id) {
        return  repository.findById(UUID.fromString(id)).orElseThrow(() -> new PerfilException("Perfil não encontrado"));
    }

    public PerfilCompletoResponseDto findByIdPerfilCompleto (String id) {
        var perfil =  findById(id);
        var perfilCond = perfilCondominioService.findByCondominioPerfil(perfil);
        List<Condominio> condominios = perfilCond.stream().map(pf -> pf.getCondominio()).collect(Collectors.toList());

        var perfilDto = toDto(perfil);
        var condominioDto = condominios.stream().map(c -> condominioService.toCondominioDto(c)).collect(Collectors.toList());

        PerfilCompletoResponseDto dto = new PerfilCompletoResponseDto();
        dto.setPerfilResponseDto(perfilDto);
        dto.setCondominios(condominioDto);

        return dto;
    }

    public Usuario findByEmail (String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new PerfilException("Perfil não encontrado"));
    }

    public Usuario findByIdPerfiPorTelefoneOrEmailAndStatusS(String filter) {
        log.info("filter " + filter);
        return repository.findPerfilPorTelefoneOrPerfilEmailAndPerfilAtivo(filter, UsuarioStatusEnum.ATIVO)
                .orElseThrow(() -> new PerfilException("Perfil não encontrado"));
    }

    public void newUsuario (@RequestBody Usuario usuario) {

        try {
            var usuarioExistente = repository.findByTelefoneOrEmail(usuario.getTelefone(), usuario.getEmail());

            if (usuarioExistente.isPresent()) {
                throw new PerfilException("Usuário já cadastrado");
            }
            var adminCondominioRole = roleRepository.findByName(Role.Values.ADMINCONDOMINIO.name());
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            usuario.setRoles(Set.of(adminCondominioRole));
            repository.save(usuario);
        } catch (Exception e) {
            log.error("Erro ao cadastrar usuario", e);
            throw e;
        }
    }

    public void newPerfilLogado (CreatePerfilDto dto, JwtAuthenticationToken token) {
        //pegar usuario logado
        var perfilToken = findById(token.getName());
        //pegar o perfil
        var condominio = perfilCondominioService.findByCondominioPerfil(perfilToken);
        List<Condominio> listCond = condominio.stream().map(UsuarioCondominio::getCondominio).toList();
        log.info("perfilCond  " + listCond);

        var condominioResponse = condominioService.findById(dto.condominio());
        log.info("condominioRequest  " + condominioResponse.getId());

        var role = roleRepository.findByName(dto.role());
        log.info("role " + role.getName());

        var perfil = new Usuario();
        perfil.setNome(dto.nome());
        perfil.setTelefone(dto.telefone());
        perfil.setEmail(dto.email());
        perfil.setPassword(passwordEncoder.encode(dto.password()));
        perfil.setRoles(Set.of(role));
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

    private PerfilResponseDto toDto(Usuario entity) {
        PerfilResponseDto dto = new PerfilResponseDto();
        dto.setPerfilId(entity.getId());
        dto.setPerfilNome(entity.getNome());
        dto.setPerfilTelefone(entity.getTelefone());
        dto.setPerfilEmail(entity.getEmail());
        dto.setPerfilFoto(entity.getFoto());
        dto.setPerfilAtivo(entity.getStatus().name());
        dto.setPerfilDataCadastro(entity.getDataCadastro());
        dto.setPerfilDataAlteracao(entity.getDataAlteracao());
        dto.setRoles(entity.getRoles());
        return dto;
    }
}






