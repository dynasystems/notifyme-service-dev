package com.notifyme.delegate;

import com.notifyme.controller.CondominoApiDelegate;
import com.notifyme.mapper.CondominoMapper;
import com.notifyme.model.CondominoRequestDTO;
import com.notifyme.persistence.Condomino;
import com.notifyme.persistence.Unidade;
import com.notifyme.services.CondominoService;
import com.notifyme.services.UnidadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CondominoDelegateImpl implements CondominoApiDelegate {

    private final CondominoService condominoService;
    private final UnidadeService unidadeService;

    @Override
    public ResponseEntity<Void> postCondominoV1(CondominoRequestDTO condominoRequestDTO) {
        Condomino condomino = CondominoMapper.INSTANCE.convert(condominoRequestDTO);

        Unidade unidade = unidadeService.getUnidade(condominoRequestDTO.getUnidadeId());

        condomino.setUnidade(unidade);

        condominoService.newCondomino(condomino);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
