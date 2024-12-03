package com.notifyme.security;

import com.notifyme.dto.login.LoginRequest;
import com.notifyme.dto.login.LoginResponse;
import com.notifyme.persistence.Role;
import com.notifyme.services.PerfilService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {

    private final PerfilService perfilService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;

    public LoginResponse login(LoginRequest loginRequest) {
        log.info("Iniciando login para o usuário {}", loginRequest.username());

        var perfil = perfilService.findByIdPerfiPorTelefoneOrEmailAndStatusS(loginRequest.username());

        if (isNull(perfil) || !perfil.isLoginCorrect(loginRequest, passwordEncoder)) {
            throw new BadCredentialsException("User ou Password is invalid!");
        }
        var now = Instant.now();
        var expiresIn= 10800L;

        var scopes= perfil.getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .issuer("vilagbackend")
                .subject(perfil.getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", scopes)
                .build();
        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return new LoginResponse(jwtValue, expiresIn);
    }

}
