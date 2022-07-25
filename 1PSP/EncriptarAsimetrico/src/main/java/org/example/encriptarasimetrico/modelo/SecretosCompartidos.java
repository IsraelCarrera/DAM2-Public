package org.example.encriptarasimetrico.modelo;

import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SecretosCompartidos {
    private int id;
    private String userACompartir;
    private String claveCifradaPublic;
}
