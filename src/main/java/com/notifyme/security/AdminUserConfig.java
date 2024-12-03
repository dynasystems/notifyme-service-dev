package com.notifyme.security;

import com.notifyme.persistence.Usuario;
import com.notifyme.persistence.Role;
import com.notifyme.persistence.enumated.UsuarioStatusEnum;
import com.notifyme.repository.PerfilRepository;
import com.notifyme.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@Configuration
public class AdminUserConfig implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;



    @Override
    @Transactional
    public void run(String... args) throws Exception {
      var roleAdmin = roleRepository.findByName(Role.Values.ADMINGERAL.name());
      var perfilAdmin = perfilRepository.findByEmail("vilag@gmail.com");

      perfilAdmin.ifPresentOrElse(
              perfil -> {
                  System.out.println("admin já existe");
                  },
              () -> {
                  var perfil = new Usuario();
                  perfil.setNome("Vilag");
                  perfil.setTelefone("18996962073");
                  perfil.setEmail("vilag@gmail.com");
                  perfil.setPassword(passwordEncoder.encode("123456"));
                  perfil.setRoles(Set.of(roleAdmin));
                  perfil.setStatus(UsuarioStatusEnum.ATIVO);
                  perfilRepository.save(perfil);
              }
      );
    }
}
