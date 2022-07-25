package org.example.common.modelo;


import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    private String nombre;
    private String pass;
    private String token;
    private String certificadoBase64;
}
