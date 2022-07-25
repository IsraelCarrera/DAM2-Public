package org.example.common.modelo;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SecretosCommon {
    private int id;
    private String nombre;
    private String secretoCifrado;
    private String firma;
    private String claveCifradaPublica;
    private String nombreDelDuenoSecreto;
}
