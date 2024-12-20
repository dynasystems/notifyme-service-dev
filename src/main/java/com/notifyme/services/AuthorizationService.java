package com.notifyme.services;

import com.notifyme.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizationService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = usuarioRepository.findByEmail(username)
                .or(() -> usuarioRepository.findByTelefone(username))
                .or(() -> usuarioRepository.findByCpf(username)).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        return user;
    }
}
