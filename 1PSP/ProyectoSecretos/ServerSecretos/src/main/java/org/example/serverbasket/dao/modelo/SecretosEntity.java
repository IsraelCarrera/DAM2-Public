package org.example.serverbasket.dao.modelo;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SecretosEntity {
    private int id;
    private String nombreUser;
    private String secreto;
    private String firma;
}
