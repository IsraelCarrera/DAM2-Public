package org.example.clientsecretos.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Secretos {
    private int id;
    private String secreto;
    //Se lo meto pra no hacer varias llamadas a la base de datos al estar en un servidor. Es la cifrada asimetricamente, la del user.
    private String passSecretoCifradaAsimetricamente;

    @Override
    public String toString() {
        return secreto;
    }
}
