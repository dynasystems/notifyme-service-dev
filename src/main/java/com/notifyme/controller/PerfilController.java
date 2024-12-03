package com.notifyme.controller;

import com.notifyme.dto.perfil.*;
import com.notifyme.persistence.Usuario;
import com.notifyme.repository.PerfilRepository;
import com.notifyme.repository.RoleRepository;
import com.notifyme.services.PerfilService;
import com.notifyme.utils.PageUtils;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/perfil")
public class PerfilController {

    @Autowired
    private PerfilService service;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper mapper;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('SCOPE_ADMINGERAL')")
    public Map<String, Object> getAllPerfil(@ModelAttribute PerfilDto filter,
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size,
                                                            @RequestParam(defaultValue = "perfilNome, asc") String[] sort) {
        List<Sort.Order> orders = PageUtils.createOrder(sort);
        Pageable pageinSort = PageRequest.of(page, size, Sort.by(orders));

        Page<Usuario> perfilPage = service.findByFilter(filter, pageinSort);
        var perfilDto = perfilPage.getContent().stream()
                .map(perfil -> mapper.map(perfil, PerfilDto.class)).collect(Collectors.toList());
        var response = PageUtils.createResponse(perfilDto, perfilPage);

        return response;
    }

    @GetMapping ("/{id}")
    public PerfilCompletoResponseDto findById (@PathVariable String id) {
      return service.findByIdPerfilCompleto(id);
    }

    @Transactional
    @PostMapping("/new-perfil")
    public void newPerfil (@RequestBody CreatePerfilDto dto) {
        service.newPerfil(dto);
    }

    @PostMapping("/new-perfil-logado")
    public void newPerfilLogado (@RequestBody CreatePerfilDto dto,
                                   JwtAuthenticationToken token) {
        service.newPerfilLogado(dto, token);
    }


}
