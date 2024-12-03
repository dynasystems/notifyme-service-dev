package com.notifyme.delegate;


import com.notifyme.controller.AutenticacaoApiDelegate;
import com.notifyme.dto.login.LoginRequest;
import com.notifyme.mapper.LoginMapper;
import com.notifyme.model.LoginRequestDTO;
import com.notifyme.model.LoginResposeDTO;
import com.notifyme.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginApiDelegateImpl  implements AutenticacaoApiDelegate {

    private final TokenService tokenService;

    @Override
    public ResponseEntity<LoginResposeDTO> postLoginV1(LoginRequestDTO loginRequestDTO)  {
        final LoginRequest loginRequest = LoginMapper.INSTANCE.convert(loginRequestDTO);
        final LoginResposeDTO loginResposeDTO = LoginMapper.INSTANCE.convertToResponseDTO(tokenService.login(loginRequest));

        return ResponseEntity.status(HttpStatus.OK).body(loginResposeDTO);
    }
}
