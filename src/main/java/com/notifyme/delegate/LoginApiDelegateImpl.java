package com.notifyme.delegate;


import com.notifyme.controller.AutenticacaoApiDelegate;
import com.notifyme.model.LoginRequestDTO;
import com.notifyme.model.LoginResposeDTO;
import com.notifyme.services.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginApiDelegateImpl  implements AutenticacaoApiDelegate {

    private final LoginService tokenService;

    @Override
    public ResponseEntity<LoginResposeDTO> postLoginV1(LoginRequestDTO loginRequestDTO)  {
        final LoginResposeDTO loginResposeDTO = tokenService.login(loginRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(loginResposeDTO);
    }
}
