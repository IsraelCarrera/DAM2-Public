package org.example.common.modelo;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Partido {
    private int idJornada;
    private String nombreEquipoLocal;
    private String nombreEquipoVisitante;
    private int puntosEquipoLocal;
    private int puntosEquipoVisitante;

    @Override
    public String toString() {
        return "idJornada: " + idJornada +
                ", nombreEquipoLocal: " + nombreEquipoLocal  +
                ", nombreEquipoVisitante:" + nombreEquipoVisitante  +
                ", Marcador: EqLocal: " + puntosEquipoLocal +  " - " + puntosEquipoVisitante + " EqVisit";
    }
}
