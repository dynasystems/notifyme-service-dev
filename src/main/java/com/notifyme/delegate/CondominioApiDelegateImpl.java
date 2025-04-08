package com.notifyme.delegate;

import com.notifyme.controller.CondominioApiDelegate;
import com.notifyme.model.CondominioBaseRequestDTO;
import com.notifyme.security.TokenService;
import com.notifyme.services.CondominioService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CondominioApiDelegateImpl implements CondominioApiDelegate {

    private final CondominioService condominioService;
    private final TokenService tokenService;

    @Override
    public ResponseEntity<Void> putCondominioV1(String authorization,
                                                 CondominioBaseRequestDTO condominioBaseRequestDTO) {

        Integer condominioId = tokenService.extractCondominioIdFromToken(authorization);
        condominioService.updateCondominio(condominioId, condominioBaseRequestDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
