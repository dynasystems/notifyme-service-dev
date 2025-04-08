package com.notifyme.services;

import com.notifyme.error.exceptions.UnidadeExistenteException;
import com.notifyme.error.exceptions.UnidadeNotFoundException;
import com.notifyme.model.UnidadeRequestDTO;
import com.notifyme.persistence.Condominio;
import com.notifyme.persistence.Unidade;
import com.notifyme.repository.UnidadeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UnidadeService {

    private final UnidadeRepository unidadeRepository;

    public void saveUnidade(Unidade unidade) {
        try {
            log.info("Salvando unidade para o condominio {}", unidade.getCondominio().getId());
            unidadeRepository.save(unidade);
        } catch (Exception e) {
            log.error("Erro ao cadastrar uma unidade");
            throw e;
        }
    }

    public Optional<Unidade> getUnidadeCondominio(String descricao, Integer condominioId) {
        try {
            log.info("Buscando unidade do condominio {}", condominioId);
            return unidadeRepository.findByDescricaoAndCondominioId(descricao, condominioId);
        } catch (Exception e) {
            log.error("Erro ao buscar unidade do condominio {}", condominioId);
            throw e;
        }
    }

    public Unidade getUnidade(Integer unidadeId) {
        try {
            log.info("Buscando unidade com o id {}", unidadeId);
            return unidadeRepository.findById(unidadeId).orElseThrow(UnidadeNotFoundException::new);
        } catch (Exception e) {
            log.info("Erro ao buscar unidade com o id {}", unidadeId);
            throw e;
        }
    }
}
