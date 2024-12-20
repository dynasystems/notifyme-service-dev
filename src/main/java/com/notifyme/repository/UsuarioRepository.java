package com.notifyme.repository;

import com.notifyme.persistence.Usuario;
import com.notifyme.persistence.enumated.UsuarioStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID>, JpaSpecificationExecutor<Usuario> {

    Optional<Usuario> findByEmail(String email);

    @Query(value = "select p from Usuario p where (p.telefone = :filter or p.email = :filter) and p.status = :status")
    Optional<Usuario> findByTelefoneOrEmailAndStatus(@Param("filter") String filter,
                                                     @Param("status") UsuarioStatusEnum status);

    Optional<Usuario> findByTelefone(String telefone);

    Optional<Usuario> findByCpf(String cpf);

    Optional<Usuario> findByTelefoneOrEmailOrCpf(String telefone, String email, String cpf);

}
