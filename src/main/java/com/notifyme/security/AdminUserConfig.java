package com.notifyme.security;

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
      var perfilAdmin = usuarioService.findByEmail("vilag@gmail.com");

      if(nonNull(perfilAdmin)) {
          System.out.println("admin já existe");
      } else {
          var perfil = new Usuario();
          perfil.setNome("Vilag");
          perfil.setTelefone("18996962073");
          perfil.setEmail("vilag@gmail.com");
          perfil.setPassword(passwordEncoder.encode("123456"));
          perfil.setRole(UserRole.ADMINGERAL);
          perfil.setStatus(UsuarioStatusEnum.ATIVO);
          usuarioService.save(perfil);
      }
    }
}
