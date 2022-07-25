package org.example.encriptar.modelo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Secretos {
    private String nombreUser;
    private String passUser;
    private String Secreto;
}
