package org.example.clientsecretos.dao;

import com.google.gson.Gson;
import io.vavr.control.Either;
import org.example.clientsecretos.dao.retrofit.SecretosAPI;
import org.example.common.modelo.Info;
import org.example.common.modelo.SecretosCommon;

import javax.inject.Inject;
import java.util.List;

public class DaoSecretos extends DaoGenerico {
    private final SecretosAPI secretosAPI;

    @Inject
    public DaoSecretos(Gson g, SecretosAPI secretosAPI) {
        super(g);
        this.secretosAPI = secretosAPI;
    }

    public Either<String, Info> addSecreto(SecretosCommon secretosCommon) {
        return construirEither(secretosAPI.addSecreto(secretosCommon));
    }

    public Either<String, List<SecretosCommon>> getAllSecretos(String nombre) {
        return construirEither(secretosAPI.getAllSecretos(nombre));
    }

    public Either<String, Info> compartirSecreto(SecretosCommon secretosCommon) {
        return construirEither(secretosAPI.compartirSecretos(secretosCommon));
    }
}
