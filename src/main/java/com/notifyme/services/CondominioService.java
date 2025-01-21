package com.notifyme.services;

import com.notifyme.error.NotifyMeErrorEnum;
import com.notifyme.error.exceptions.CondominioExistenteException;
import com.notifyme.error.exceptions.CustomException;
import com.notifyme.persistence.Condominio;
import com.notifyme.persistence.Usuario;
import com.notifyme.repository.CondominioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class CondominioService {

    private final CondominioRepository condominioRepository;
    private final UsuarioCondominioService usuarioCondominioService;


    @Transactional
    public void newCondominio(Usuario usuario, Condominio condominio) {
        try {
            log.info("Cadastrando um novo condominio");

            Optional<Condominio> optCondominio = condominioRepository.findByCnpj(condominio.getCnpj());
            validaCondominio(condominio, optCondominio);

            Condominio novoCondominio = condominioRepository.save(condominio);
            usuarioCondominioService.saveAssociacao(usuario, novoCondominio);
        } catch (Exception e) {
            log.error("Erro ao cadastrar o condominio");
            throw  e;
        }
    }

    private static void validaCondominio(Condominio condominio, Optional<Condominio> optCondominio) {

        if (optCondominio.isPresent()) {
            throw new CondominioExistenteException();
        }

    }
}
