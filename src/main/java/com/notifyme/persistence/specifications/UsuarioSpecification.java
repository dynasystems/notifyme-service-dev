package com.notifyme.persistence.specifications;

import com.notifyme.persistence.Usuario;
import com.notifyme.persistence.UsuarioCondominio;
import com.notifyme.persistence.enumated.UsuarioStatusEnum;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UsuarioSpecification {

    public static Specification<Usuario> filtrarUsuarios(
            Integer condominioId, String nome, String cpf,
            String telefone, String email, UsuarioStatusEnum status) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (condominioId != null) {
                Join<Usuario, UsuarioCondominio> join = root.join("usuarioCondominios");
                predicates.add(criteriaBuilder.equal(join.get("condominio").get("id"), condominioId));
            }

            if (nome != null && !nome.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("nome")), "%" + nome.toLowerCase() + "%"));
            }

            if (cpf != null && !cpf.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("cpf"), cpf));
            }

            if (telefone != null && !telefone.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("telefone"), telefone));
            }

            if (email != null && !email.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("email"), email));
            }

            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}