package org.example.common.modelo;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioLoggear {
    private String nombre;
    private String certificadoBase64;
}
