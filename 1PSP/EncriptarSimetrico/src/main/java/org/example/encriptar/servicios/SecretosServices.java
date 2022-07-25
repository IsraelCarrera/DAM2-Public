package org.example.encriptar.servicios;

import io.vavr.control.Either;
import org.example.encriptar.dao.DaoSecretos;
import org.example.encriptar.modelo.Secretos;

import javax.inject.Inject;
import java.util.List;

public class SecretosServices {
    private final DaoSecretos dao;

    @Inject
    public SecretosServices(DaoSecretos dao) {
        this.dao = dao;
    }

    public Either<String, Boolean> addSecreto(Secretos s) {
        return dao.addSecreto(s);
    }

    public Either<String, List<String>> getSecretos(String user, String pass) {
        return dao.consultarSecretos(user, pass);
    }
}
