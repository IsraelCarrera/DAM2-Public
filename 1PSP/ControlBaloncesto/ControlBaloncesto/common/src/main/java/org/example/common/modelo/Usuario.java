package org.example.common.modelo;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    private int idUser;
    private String nombreUser;
    private String pass;
    private String correoElectronico;
    private String codigoActivacion;
    private String codigoSerAdmin;
    private int idTipoUsuario;
    private boolean estaActivo;

    @Override
    public String toString() {
        return
                "idUser: " + idUser +
                ", nombreUser:" + nombreUser +
                ", correoElectronico:" + correoElectronico +
                ", idTipoUsuario:" + idTipoUsuario +
                ", estaActivo:" + estaActivo ;
    }
}
