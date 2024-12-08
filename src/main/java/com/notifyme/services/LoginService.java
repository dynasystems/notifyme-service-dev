package com.notifyme.services;

import com.notifyme.dto.login.LoginRequest;
import com.notifyme.dto.login.LoginResponse;
import com.notifyme.persistence.Usuario;
import com.notifyme.repository.UsuarioRepository;
import com.notifyme.security.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final TokenService tokenService;

    public LoginResponse login(LoginRequest loginRequest) {
        log.info("Iniciando login para o usuário {}", loginRequest.username());
        var usernameAndPassword = new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password());
        var auth = this.authenticationManager.authenticate(usernameAndPassword);

        var token =  tokenService.generatedToken((Usuario) auth.getPrincipal());

        return new LoginResponse(token);
    }

}
