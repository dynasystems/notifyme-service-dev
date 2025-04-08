package com.notifyme.delegate;

import com.notifyme.controller.UploadApiDelegate;
import com.notifyme.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UploadApiDelegateImpl implements UploadApiDelegate {

    private final UsuarioService usuarioService;

    public ResponseEntity<Void> upFotoPerfilV1(Integer id, MultipartFile fotoPerfil) {
        usuarioService.uploadFotoPerfil(id, fotoPerfil);
        return new ResponseEntity<>(HttpStatus.OK);

    }


}
