package org.example.clientsecretos.servicios.Modelo;

import lombok.*;

import java.security.PrivateKey;
import java.security.PublicKey;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Claves {
    private PublicKey publicKey;
    private PrivateKey privateKey;
}
