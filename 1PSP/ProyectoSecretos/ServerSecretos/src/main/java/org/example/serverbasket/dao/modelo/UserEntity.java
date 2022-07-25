package org.example.serverbasket.dao.modelo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {
    private int id;
    private String nombre;
    private String certificado;
}
