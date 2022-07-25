package org.example.serverbasket.servicios;

import io.vavr.control.Either;
import org.example.common.modelo.ApiError;
import org.example.common.modelo.Info;
import org.example.common.modelo.SecretosCommon;
import org.example.serverbasket.dao.DaoSecretos;

import jakarta.inject.Inject;
import org.example.serverbasket.ee.utils.Constantes;

import java.util.List;


public class ServiciosSecretos {
    private final DaoSecretos dao;

    @Inject
    public ServiciosSecretos(DaoSecretos dao) {
        this.dao = dao;
    }

    public Either<ApiError, Info> addSecretosJuntos(SecretosCommon secretosCommon) {
        Either<ApiError, Info> resultado;
        //Primero añadimos el secreto a la tabla secreto
        Either<ApiError, Integer> eitherAddSecreto = dao.addSecreto(secretosCommon);
        if (eitherAddSecreto.isRight()) {
            //Añadimos al Secretos el id del secreto.
            secretosCommon.setId(eitherAddSecreto.get());
            //Posteriormente añadimos los datos a la tabla secreto Compartidos
            Either<ApiError, Boolean> eitherAddSecretoCompartido = dao.addSecretoCompartido(secretosCommon);
            if (eitherAddSecretoCompartido.isRight()) {
                //Y aquí ya se han agregado las dos cosas a sus respectivas tablas.
                resultado = Either.right(Info.builder().mensaje(Constantes.SE_HAN_ANADIDO_CON_EXITO).build());
            } else {
                resultado = Either.left(eitherAddSecretoCompartido.getLeft());
            }
        } else {
            resultado = Either.left(eitherAddSecreto.getLeft());
        }
        return resultado;
    }

    public Either<ApiError, List<SecretosCommon>> getAllSecretos(String nombre) {
        return dao.getAllSecretosCompartidos(nombre);
    }

    public Either<ApiError, Info> compartirSecreto(SecretosCommon secretosCommon) {
        Either<ApiError, Info> resultado;
        Either<ApiError, Boolean> eitherCompartido = dao.addSecretoCompartido(secretosCommon);
        if (eitherCompartido.isRight()) {
            resultado = Either.right(Info.builder().mensaje(Constantes.SECRETO_ANADIDO_CON_EXITO).build());
        } else {
            resultado = Either.left(eitherCompartido.getLeft());
        }
        return resultado;
    }
}
