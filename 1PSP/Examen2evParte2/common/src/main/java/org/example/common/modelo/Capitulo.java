package org.example.common.modelo;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Capitulo {
    private int id;
    private String nombreCap;
    private boolean haSidoVisto;
    private int idUser;
    private int idSerie;
}
