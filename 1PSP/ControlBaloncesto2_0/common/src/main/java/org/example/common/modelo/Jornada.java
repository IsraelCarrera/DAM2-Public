package org.example.common.modelo;

import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Jornada {
    private int idJornada;
    private LocalDate fechaJornada;

    @Override
    public String toString() {
        return "idJornada:" + idJornada + " fechaJornada:" + fechaJornada;
    }
}
