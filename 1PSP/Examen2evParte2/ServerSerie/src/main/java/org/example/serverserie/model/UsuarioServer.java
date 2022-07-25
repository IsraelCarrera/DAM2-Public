package org.example.serverserie.model;

import lombok.*;
import org.example.common.modelo.Serie;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UsuarioServer {
    private int id;
    private String nombre;
    private String passHash;
    private String tipo;
    private List<Serie> series;
}
