package com.notifyme.services;

import com.notifyme.error.exceptions.UsuarioNotFoundException;
import com.notifyme.persistence.Usuario;
import com.notifyme.persistence.UsuarioCondominio;
import com.notifyme.repository.PerfilCondominioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PerfilCondominioService {

    @Autowired
    private PerfilCondominioRepository repository;

    public UsuarioCondominio findById(String id) {
       return repository.findById(UUID.fromString(id)).orElseThrow(UsuarioNotFoundException::new);
    }
    public List<UsuarioCondominio> findByCondominioPerfil(Usuario perfil) {
        return repository.findByUsuario(perfil);
    }
    public List<UsuarioCondominio> findByCondominioPerfilId(String id) {
        return repository.findByUsuarioId(UUID.fromString(id));
    }

    public void save(UsuarioCondominio perfilCondominio) {
        repository.save(perfilCondominio);
    }

}
