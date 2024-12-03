package com.notifyme.repository;

import com.notifyme.dto.condominio.CondominioResponseDto;
import com.notifyme.persistence.Condominio;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public interface CondominioRepository extends JpaRepository<Condominio, UUID>, JpaSpecificationExecutor<Condominio> {


    interface Specs {

        static Specification<Condominio> byFilter(CondominioResponseDto filtro) {
            return (root, query, builder) -> {
                List<Predicate> predicates = new ArrayList<>();

                if (StringUtils.isNotEmpty(filtro.getCondominioNome())) {
                    Predicate pd = builder.like(builder.lower(root.get("condominioNome")),
                            "%" + filtro.getCondominioNome().trim().toLowerCase() + "%");
                    predicates.add(pd);
                }

                if (StringUtils.isNotEmpty(filtro.getCondominioTelefone())) {
                    Predicate pd = builder.like(builder.lower(root.get("condominioTelefone")),
                            "%" + filtro.getCondominioTelefone().trim().toLowerCase() + "%");
                    predicates.add(pd);
                }

                if (StringUtils.isNotEmpty(filtro.getCondominioLogradouro())) {
                    Predicate pd = builder.like(builder.lower(root.get("condominioLogradouro")),
                            "%" + filtro.getCondominioLogradouro().trim().toLowerCase() + "%");
                    predicates.add(pd);
                }

                if (StringUtils.isNotEmpty(filtro.getCondominioNumero())) {
                    Predicate pd = builder.like(builder.lower(root.get("condominioNumero")),
                            "%" + filtro.getCondominioNumero().trim().toLowerCase() + "%");
                    predicates.add(pd);
                }

                if (StringUtils.isNotEmpty(filtro.getCondominioCep())) {
                    Predicate pd = builder.like(builder.lower(root.get("conddominioCep")),
                            "%" + filtro.getCondominioCep().trim().toLowerCase() + "%");
                    predicates.add(pd);
                }

                if (StringUtils.isNotEmpty(filtro.getCondominioEstado())) {
                    Predicate pd = builder.like(builder.lower(root.get("condominioEstado")),
                            "%" + filtro.getCondominioEstado().trim().toLowerCase() + "%");
                    predicates.add(pd);
                }

                if (StringUtils.isNotEmpty(filtro.getCondominioCidade())) {
                    Predicate pd = builder.like(builder.lower(root.get("condominioCidade")),
                            "%" + filtro.getCondominioCidade().trim().toLowerCase() + "%");
                    predicates.add(pd);
                }

                return builder.and(predicates.toArray(new Predicate[0]));
            };
        }
    }
}