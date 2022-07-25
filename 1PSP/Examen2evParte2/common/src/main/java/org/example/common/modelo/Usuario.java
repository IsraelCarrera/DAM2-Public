package org.example.common.modelo;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Usuario {
    private int id;
    private String nombre;
    private String pass;
    private String tipo;
    private String token;
    private List<Serie> series;
}
