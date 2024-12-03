package com.notifyme.persistence;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.util.UUID;

@Data
@Entity
@Table(name = "NM_ROLE")
public class Role {

    @Id
    @GeneratedValue
    @Column(name = "ID", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "NAME")
    private String name;

    @Getter
    public enum Values {
        ADMINGERAL(1L),
        ADMINCONDOMINIO(2L),
        USUARIOCONDOMINIO(3L),
        PERFILMORADOR(4L);

        long id;

        Values(long roleId) {
            this.id = roleId;
        }
    }
}
