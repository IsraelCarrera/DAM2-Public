package org.example.clientbasket.servicios;

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

    public Either<String, Usuario> addUser(Usuario u) {
        return dao.addUsuario(u);
    }

    public Either<String, Usuario> hacerLogging(String nombreUser, String pass) {
        return dao.hacerLogging(nombreUser, pass);
    }

    public Either<String, List<Usuario>> getAllUser() {
        return dao.getAllUser();
    }

    public Either<String, Avisos> cambiarPass(String nombreUser) {
        return dao.cambiarPass(nombreUser);
    }

    public Either<String, Integer> deleteUser(int idUser) {
        return dao.deleteUser(idUser);
    }

    public Either<String, Avisos> hacerAdmin(int id, String correo) {
        return dao.hacerAdmin(id, correo);
    }
}
