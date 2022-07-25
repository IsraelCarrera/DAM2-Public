package org.example.encriptar.dao.model;


import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SecretosEntity {
    private int id;
    private String nombre;
    private String secreto;
}
