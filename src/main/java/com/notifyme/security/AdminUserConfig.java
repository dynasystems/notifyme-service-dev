package com.notifyme.security;

import com.notifyme.error.exceptions.UsuarioNotFoundException;
import com.notifyme.persistence.Usuario;
import com.notifyme.persistence.enumated.UserRole;
import com.notifyme.persistence.enumated.UsuarioStatusEnum;
import com.notifyme.repository.UsuarioRepository;
import com.notifyme.services.UsuarioService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static java.util.Objects.nonNull;

@Configuration
@RequiredArgsConstructor
public class AdminUserConfig implements CommandLineRunner {

    private final UsuarioService usuarioService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
      try {
          var perfilAdmin = usuarioService.findByEmail("vilag@gmail.com");
          System.out.println("admin já existe");
      } catch (UsuarioNotFoundException e) {
          var usuario = new Usuario();
          usuario.setNome("Vilag");
          usuario.setTelefone("18996962073");
          usuario.setEmail("vilag@gmail.com");
          usuario.setCpf("79222300017");
          usuario.setPassword(passwordEncoder.encode("123456"));
          usuario.setRole(UserRole.ADMINGERAL);
          usuario.setStatus(UsuarioStatusEnum.ATIVO);
          usuarioService.save(usuario);
      }
    }
}
