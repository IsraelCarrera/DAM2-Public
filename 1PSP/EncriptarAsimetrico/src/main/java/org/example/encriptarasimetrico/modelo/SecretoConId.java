package org.example.encriptarasimetrico.modelo;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SecretoConId {
    private int id;
    private String secreto;

    @Override
    public String toString() {
        return secreto;
    }
}
