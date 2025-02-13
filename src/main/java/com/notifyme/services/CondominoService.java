package com.notifyme.services;

import com.notifyme.error.exceptions.CondominoExistenteNaUnidadeException;
import com.notifyme.persistence.Condomino;
import com.notifyme.repository.CondominoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CondominoService {

    private final CondominoRepository condominoRepository;

    public void newCondomino(Condomino condomino) {
        try {
            validaCondominoNaUnidade(condomino);
            condominoRepository.save(condomino);
        }catch (Exception e) {
            log.error("Erro ao cadastrar condomino na unidade");
            throw e;
        }
    }

    private void validaCondominoNaUnidade(Condomino condomino) {
        List<Condomino> allCondominoByUnidade = condominoRepository.getAllCondominoByUnidade(condomino.getUnidade());

        List<Condomino> condominoFiltrado = allCondominoByUnidade.stream()
                .filter(c -> c.getNome().equals(condomino.getNome())).toList();

        if(!condominoFiltrado.isEmpty()) {
           throw new CondominoExistenteNaUnidadeException();
       }
    }

}
