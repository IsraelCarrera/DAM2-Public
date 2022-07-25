package org.example.common.modelo;


import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Equipo {
    private String nombreEquipo;

    @Override
    public String toString() {
        return nombreEquipo;
    }
}
