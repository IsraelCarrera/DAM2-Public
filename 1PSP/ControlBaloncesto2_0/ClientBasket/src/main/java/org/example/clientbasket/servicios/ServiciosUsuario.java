package org.example.clientbasket.servicios;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import org.example.clientbasket.dao.DaoUsuarios;
import org.example.common.modelo.Avisos;
import org.example.common.modelo.Usuario;

import javax.inject.Inject;
import java.util.List;

public class ServiciosUsuario {
    private final DaoUsuarios dao;

    @Inject
    public ServiciosUsuario(DaoUsuarios dao) {
        this.dao = dao;
    }

    public Single<Either<String, Usuario>> addUser(Usuario u) {
        return dao.addUsuario(u);
    }

    public Single<Either<String, Usuario>> hacerLogging(String nombreUser, String pass) {
        return dao.hacerLogging(nombreUser, pass);
    }

    public Single<Either<String, List<Usuario>>> getAllUser() {
        return dao.getAllUser();
    }

    public Single<Either<String, Avisos>> cambiarPass(String nombreUser) {
        return dao.cambiarPass(nombreUser);
    }

    public Single<Either<String, Integer>> deleteUser(int idUser) {
        return dao.deleteUser(idUser);
    }

    public Single<Either<String, Avisos>> hacerAdmin(int id, String correo) {
        return dao.hacerAdmin(id, correo);
    }
}
