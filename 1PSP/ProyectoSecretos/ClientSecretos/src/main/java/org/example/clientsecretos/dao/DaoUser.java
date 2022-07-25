package org.example.clientsecretos.dao;

import com.google.gson.Gson;
import io.vavr.control.Either;
import org.example.clientsecretos.dao.retrofit.UserAPI;
import org.example.common.modelo.User;
import org.example.common.modelo.UsuarioCrear;
import org.example.common.modelo.UsuarioLoggear;

import javax.inject.Inject;
import java.util.List;

public class DaoUser extends DaoGenerico {
    private final UserAPI userAPI;

    @Inject
    public DaoUser(Gson g, UserAPI userAPI) {
        super(g);
        this.userAPI = userAPI;
    }

    //Añadir un user.
    public Either<String, UsuarioLoggear> addUser(UsuarioCrear usuarioCrear) {
        return construirEither(userAPI.addUser(usuarioCrear));
    }

    public Either<String, User> hacerLoggin(UsuarioLoggear usuarioLoggear) {
        return construirEither(userAPI.hacerLoggin(usuarioLoggear.getNombre(), usuarioLoggear.getCertificadoBase64()));
    }

    public Either<String, List<User>> getAllUsers() {
        return construirEither(userAPI.getAllUsers());
    }
}
