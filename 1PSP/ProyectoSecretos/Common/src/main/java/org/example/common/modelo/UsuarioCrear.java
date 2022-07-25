package org.example.common.modelo;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioCrear {
    private String nombre;
    private String publicKeyBase64;
}
