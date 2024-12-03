package com.notifyme.delegate;

import com.notifyme.controller.CepApiDelegate;
import com.notifyme.model.CepResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CepApiDelegateImpl  implements CepApiDelegate {


    @Override
    public ResponseEntity<CepResponseDTO> getCepV1(String estado,
                                                    String cidade,
                                                    String endereco) {

        CepResponseDTO cepResponseDTO = new CepResponseDTO();
        cepResponseDTO.setCep("cep");
        cepResponseDTO.setBairro("bairro");
        cepResponseDTO.setLocalidade("localidade");

        return new ResponseEntity<>(cepResponseDTO, HttpStatus.OK);
    }
}
