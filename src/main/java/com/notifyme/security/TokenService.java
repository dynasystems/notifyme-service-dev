package com.notifyme.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.notifyme.dto.login.LoginRequest;
import com.notifyme.model.LoginRequestDTO;
import com.notifyme.persistence.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {

    @Value("${jwt.public.key}")
    private RSAPublicKey publicKey;

    @Value("${jwt.private.key}")
    private RSAPrivateKey privateKey;

    public String generatedToken(Usuario usuario, LoginRequestDTO loginRequest) {
        try {
            Algorithm algorithm = Algorithm.RSA256(null, privateKey);
            String token = JWT.create()
                    .withIssuer("notify-api")
                    .withSubject(loginRequest.getUsername())
                    .withExpiresAt(genExpirationDate())
                    .withClaim("role", usuario.getRole().getRole())
                    .sign(algorithm);
            return token;
        } catch (Exception exception) {
             throw new RuntimeException("Error while generating token", exception);
        }
    }

    public String validateToken(String token) {
        Algorithm algorithm = Algorithm.RSA256(publicKey, null);
        return JWT.require(algorithm)
                .withIssuer("notify-api")
                .build()
                .verify(token)
                .getSubject();
    }

    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(3).toInstant(ZoneOffset.of("-03:00"));
    }

}
