package org.example.common.modelo;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Serie {
    private int id;
    private String nombre;
    private List<Capitulo> capitulos;
    private int idUser;
}
