package com.notifyme.delegate;

import com.notifyme.controller.UnidadeApiDelegate;
import com.notifyme.error.exceptions.UnidadeExistenteException;
import com.notifyme.model.UnidadeRequestDTO;
import com.notifyme.persistence.Condominio;
import com.notifyme.persistence.Unidade;
import com.notifyme.services.CondominioService;
import com.notifyme.services.UnidadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UnidadeApiDelegateImpl implements UnidadeApiDelegate {

    private final UnidadeService unidadeService;
    private final CondominioService condominioService;

    @Override
    public ResponseEntity<Void> postUnidadeV1(UnidadeRequestDTO unidadeRequestDTO) {

        Condominio optUnidade = condominioService.getCondominioById(unidadeRequestDTO.getCondominioId());
        validaUnidade(unidadeRequestDTO, optUnidade);

        Unidade unidade = new Unidade();
        unidade.setCondominio(optUnidade);
        unidade.setDescricao(unidadeRequestDTO.getDescricao());

        unidadeService.saveUnidade(unidade);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    private void validaUnidade(UnidadeRequestDTO unidadeRequestDTO, Condominio optUnidade) {
        Optional<Unidade> unidadeCondominio = unidadeService.getUnidadeCondominio(unidadeRequestDTO.getDescricao(), optUnidade.getId());

        if (unidadeCondominio.isPresent()) {
            throw  new UnidadeExistenteException();
        }
    }
}
