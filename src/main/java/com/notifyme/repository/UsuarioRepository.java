package com.notifyme.repository;

import com.notifyme.dto.perfil.PerfilDto;
import com.notifyme.persistence.Usuario;
import com.notifyme.persistence.enumated.UsuarioStatusEnum;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID>, JpaSpecificationExecutor<Usuario> {

    Optional<Usuario> findByEmail(String email);

    @Query(value = "select p  from Usuario p where (p.telefone = :filter or p.email = : filter) and p.status = :status")
    Optional<Usuario> findPerfilPorTelefoneOrPerfilEmailAndPerfilAtivo(@Param("filter") String filter,
                                                                       @Param("status") UsuarioStatusEnum status);

    Optional<Usuario> findByTelefoneOrEmail(String telefone, String email);


    interface Specs {

        static Specification<Usuario> byFilter(PerfilDto filtro) {
            return (root, query, builder) -> {
                List<Predicate> predicates = new ArrayList<>();

                if (StringUtils.isNotEmpty(filtro.getPerfilNome())) {
                    Predicate pd = builder.like(builder.lower(root.get("perfilNome")),
                            "%" + filtro.getPerfilNome().trim().toLowerCase() + "%");
                    predicates.add(pd);
                }

                if (StringUtils.isNotEmpty(filtro.getPerfilTelefone())) {
                    Predicate pd = builder.like(builder.lower(root.get("perfilTelefone")),
                            "%" + filtro.getPerfilTelefone().trim().toLowerCase() + "%");
                    predicates.add(pd);
                }

                if (StringUtils.isNotEmpty(filtro.getPerfilEmail())) {
                    Predicate pd = builder.like(builder.lower(root.get("perfilEmail")),
                            "%" + filtro.getPerfilEmail().trim().toLowerCase() + "%");
                    predicates.add(pd);
                }

                return builder.and(predicates.toArray(new Predicate[0]));
            };
        }
    }

}
