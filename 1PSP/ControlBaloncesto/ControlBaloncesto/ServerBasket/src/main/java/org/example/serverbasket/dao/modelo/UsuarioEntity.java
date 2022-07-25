package org.example.serverbasket.dao.modelo;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioEntity {
    private int idUser;
    private String nombreUser;
    private String passHash;
    private String codActivacion;
    private String correoElectronico;
    private boolean estaActivo;
    private LocalDateTime fechaLimite;
    private int idTipoUsuario;
    private String codRestPass;
    private String codigoSerAdmin;


}
