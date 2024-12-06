package com.notifyme.delegate;

import com.notifyme.controller.UsuarioApiDelegate;
import com.notifyme.mapper.UsuarioMapper;
import com.notifyme.model.UpdateUsuarioRequestDTO;
import com.notifyme.model.UsuarioRequestDTO;
import com.notifyme.persistence.Usuario;
import com.notifyme.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioApiDelegateImpl implements UsuarioApiDelegate {

    private final UsuarioService usuarioService;

    @Override
    public ResponseEntity<Void> postUsuarioV1(UsuarioRequestDTO usuarioRequestDTO) {
        Usuario newUsuario = UsuarioMapper.INSTANCE.convert(usuarioRequestDTO);
        usuarioService.newUsuario(newUsuario);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> putUsuarioV1(String id, UpdateUsuarioRequestDTO updateUsuarioRequestDTO) {

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
