package com.notifyme.services;

import com.notifyme.dto.login.LoginRequest;
import com.notifyme.dto.login.LoginResponse;
import com.notifyme.error.exceptions.CredenciaisInvalidException;
import com.notifyme.error.exceptions.UsuarioNotFoundException;
import com.notifyme.persistence.Usuario;
import com.notifyme.security.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public LoginResponse login(LoginRequest loginRequest) {
        log.info("Iniciando login para o usuário {}", loginRequest.username());
        var auth = authenticate(loginRequest);
        var token =  tokenService.generatedToken((Usuario) auth.getPrincipal());
        return new LoginResponse(token);
    }

    private Authentication authenticate(LoginRequest loginRequest) {
        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));
        } catch (UsernameNotFoundException e) {
            throw new UsuarioNotFoundException();
        } catch (BadCredentialsException b) {
            throw new CredenciaisInvalidException();
        } catch (Exception t) {
            log.error("Erro ao autenticar usuário", t);
            throw t;
        }
    }
}
