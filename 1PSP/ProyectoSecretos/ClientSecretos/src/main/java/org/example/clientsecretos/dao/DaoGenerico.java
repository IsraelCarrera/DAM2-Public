package org.example.clientsecretos.dao;

import com.google.gson.Gson;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import okhttp3.MediaType;
import org.example.clientsecretos.config.util.Constantes;
import org.example.common.modelo.ApiError;
import retrofit2.Call;
import retrofit2.HttpException;
import retrofit2.Response;

import javax.inject.Inject;
import java.util.Objects;

@Log4j2
abstract class DaoGenerico {
    private final Gson g;

    @Inject
    public DaoGenerico(Gson g) {
        this.g = g;
    }

    public <T> Either<String, T> construirEither(Call<T> call) {
        Either<String, T> resultado = null;

        try {
            Response<T> response = call.execute();
            if (response.isSuccessful()) {
                resultado = Either.right(response.body());
            } else {
                if (response.errorBody().contentType().equals(MediaType.get(Constantes.APPLICATION_JSON))) {
                    ApiError apiError = g.fromJson(response.errorBody().string(), ApiError.class);
                    resultado = Either.left(apiError.getMensaje());
                } else {
                    resultado = Either.left(response.message());
                }
            }
        } catch (Exception e) {
            resultado = Either.left(Constantes.ERROR_EN_LA_CONEXION_CON_EL_SERVIDOR_INTENTELO_MAS_TARDE);
            log.error(e.getMessage(), e);

        }
        return resultado;
    }
}
