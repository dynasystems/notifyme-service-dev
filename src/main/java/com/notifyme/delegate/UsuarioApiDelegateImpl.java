package com.notifyme.delegate;

import com.notifyme.controller.UsuarioApiDelegate;
import com.notifyme.mapper.UsuarioMapper;
import com.notifyme.model.NovaSenhaRequestDTO;
import com.notifyme.model.PostUsuarioRedefinirSenhaV1Request;
import com.notifyme.model.PostUsuarioValidaTokenV1Request;
import com.notifyme.model.UpdateUsuarioRequestDTO;
import com.notifyme.model.UsuarioRequestDTO;
import com.notifyme.persistence.Usuario;
import com.notifyme.services.UsuarioActivationService;
import com.notifyme.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioApiDelegateImpl implements UsuarioApiDelegate {

    private final UsuarioService usuarioService;
    private final UsuarioActivationService usuarioActivationService;

    @Override
    public ResponseEntity<Void> postUsuarioV1(UsuarioRequestDTO usuarioRequestDTO) {
        Usuario newUsuario = UsuarioMapper.INSTANCE.convert(usuarioRequestDTO);
        usuarioService.novoUsuario(newUsuario);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Void> putUsuarioV1(String id, UpdateUsuarioRequestDTO updateUsuarioRequestDTO) {
        usuarioService.updateUsuario(id, updateUsuarioRequestDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    public ResponseEntity<Void> deleteUsuario(String id) {
        usuarioService.deleteUsuario(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    public ResponseEntity<Void> postUsuarioValidaTokenV1(PostUsuarioValidaTokenV1Request postUsuarioValidaTokenV1Request) {
          usuarioActivationService.validaToken(postUsuarioValidaTokenV1Request.getToken());
          return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Void> postUsuarioRedefinirSenhaV1(PostUsuarioRedefinirSenhaV1Request postUsuarioRedefinirSenhaV1Request) {
        usuarioService.redefinirSenha(postUsuarioRedefinirSenhaV1Request);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    public ResponseEntity<Void> postUsuarioNovaSenhaV1(String id, NovaSenhaRequestDTO novaSenhaRequestDTO) {
        usuarioService.novaSenha(id, novaSenhaRequestDTO);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
