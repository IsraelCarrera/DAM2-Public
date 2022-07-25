package org.example.clientserie.servicios;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import org.example.clientserie.dao.DaoUsuario;
import org.example.common.modelo.Usuario;

public class UsuarioServicios {
    private final DaoUsuario dao;

    public UsuarioServicios() {
        this.dao = new DaoUsuario();
    }

    public Single<Either<String, Usuario>> addUser(Usuario usuario){
        return dao.addUser(usuario);
    }

    public Single<Either<String, Usuario>> getUserLoggin(String nombre, String pass){
        return dao.getUserLoggin(nombre, pass);
    }
}
