package org.example.serverbasket.dao.modelo;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SecretosCompartidosEntity {
    private int id;
    private String userACompartir;
    private String claveCifradaPublica;
}
