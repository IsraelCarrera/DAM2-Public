package org.example.serverbasket.dao.modelo;


import lombok.*;

@Builder
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PartidoEntity {
    private int idPartido;
    private int idJornada;
    private int idEquipoLocal;
    private int idEquipoVisitante;
    private String resultado;
}
