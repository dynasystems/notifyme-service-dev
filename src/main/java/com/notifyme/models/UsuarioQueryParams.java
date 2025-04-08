package com.notifyme.models;

import com.notifyme.model.StatusUsuario;
import com.notifyme.persistence.enumated.UsuarioStatusEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderClassName = "UsuarioQueryParamsInternal")
public class UsuarioQueryParams {

    private Integer condominioId;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private UsuarioStatusEnum usuarioStatusEnum;
    private Integer pageSize;
    private Integer pageNumber;

    public static class UsuarioQueryParamsInternal {

        public UsuarioQueryParamsInternal condomionio(Integer condominioId) {
            if (condominioId != null) {
                this.condominioId = condominioId;
            }

            return this;
        }
        public UsuarioQueryParamsInternal nome(String nome) {
            if (nome != null) {
                this.nome = nome;
            }

            return this;
        }

        public UsuarioQueryParamsInternal cpf(String cpf) {
            if (cpf != null) {
                this.cpf = cpf;
            }

            return this;
        }

        public UsuarioQueryParamsInternal telefone(String telefone) {
            if (telefone != null) {
                this.telefone = telefone;
            }

            return this;
        }

        public UsuarioQueryParamsInternal status(StatusUsuario status) {
            if (status != null) {
                this.usuarioStatusEnum = UsuarioStatusEnum.valueOf(status.name());
            }

            return this;
        }
    }
}
