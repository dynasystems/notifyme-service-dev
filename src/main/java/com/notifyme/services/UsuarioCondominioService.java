package com.notifyme.services;

import com.notifyme.persistence.Condominio;
import com.notifyme.persistence.Usuario;
import com.notifyme.persistence.UsuarioCondominio;
import com.notifyme.repository.UsuarioCondominioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioCondominioService {

    private final UsuarioCondominioRepository usuarioCondominioRepository;

    public void saveAssociacao(Usuario usuario, Condominio condominio) {
        try {
            log.info("Criando associação entre novo usuario e condominio");
            UsuarioCondominio usuarioCondominio = new UsuarioCondominio();
            usuarioCondominio.setUsuario(usuario);
            usuarioCondominio.setCondominio(condominio);
            usuarioCondominioRepository.save(usuarioCondominio);
        } catch (Exception e) {
            log.error("Erro ao associar usuario com condominio");
            throw e;
        }
    }
}
