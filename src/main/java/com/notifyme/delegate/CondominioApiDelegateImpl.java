package com.notifyme.delegate;

import com.notifyme.controller.CondominioApiDelegate;
import com.notifyme.mapper.CondominioMapper;
import com.notifyme.model.CondominioRequestDTO;
import com.notifyme.persistence.Condominio;
import com.notifyme.persistence.Usuario;
import com.notifyme.services.CondominioService;
import com.notifyme.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CondominioApiDelegateImpl implements CondominioApiDelegate {

    private final CondominioService condominioService;
    private final UsuarioService usuarioService;

    public ResponseEntity<Void> postCondominioV1(CondominioRequestDTO condominioRequestDTO) {
        Usuario usuario = usuarioService.findById(condominioRequestDTO.getUsuarioId());
        Condominio condominio = CondominioMapper.INSTANCE.convert(condominioRequestDTO);
        condominioService.newCondominio(usuario, condominio);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
