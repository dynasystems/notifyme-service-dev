package com.notifyme.controller;

import com.notifyme.dto.condominio.CondominioResponseDto;
import com.notifyme.dto.condominio.CondominioRequest;
import com.notifyme.dto.condominio.CondominioResponse;
import com.notifyme.persistence.Condominio;
import com.notifyme.services.CondominioService;
import com.notifyme.utils.PageUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/condominio")
public class CondominioController {

    @Autowired
    private CondominioService service;

    @Autowired
    private ModelMapper mapper;

    @GetMapping("/all")
    public Map<String, Object> getAllPerfil(@ModelAttribute CondominioResponseDto filter,
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size,
                                                            @RequestParam(defaultValue = "condominioNome, asc") String[] sort) {
        List<Sort.Order> orders = PageUtils.createOrder(sort);
        Pageable pageinSort = PageRequest.of(page, size, Sort.by(orders));

        Page<Condominio> condominioPage = service.findByFilter(filter, pageinSort);
        var condominioDto = condominioPage.getContent().stream()
                .map(perfil -> mapper.map(perfil, CondominioResponseDto.class)).collect(Collectors.toList());
        var response = PageUtils.createResponse(condominioDto, condominioPage);

        return response;
    }


    @GetMapping ("/{id}")
    public Condominio findById (@PathVariable String id) {
        return service.findById(id);
    }

//    @PostMapping ("/new")
//    public CondominioResponse save (@RequestBody CondominioRequest dto, JwtAuthenticationToken token) {
//        return service.newCondominio(dto, token);
//    }

//    @PutMapping ("/update/{id}")
//    public CondominioDto edit (@PathVariable Long id, @RequestBody @Valid  CondominioDto dto) {
//        return service.edit(id, dto);
//    }

    @DeleteMapping ("/delete/{id}")
    public void delete (@PathVariable String id) {
        service.delete(id);
    }
}
