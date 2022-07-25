package dao;

import dao.data.ApiError;
import dao.data.Trabajador;
import dao.retrofit.PrimeraAPI;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import retrofit2.Response;

import javax.inject.Inject;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Log4j2
public class DaoTrabajador {
    private final PrimeraAPI primeraAPI;

    @Inject
    public DaoTrabajador(PrimeraAPI primeraAPI) {
        this.primeraAPI = primeraAPI;
    }

    public Either<ApiError, List<Trabajador>> getAllTrabajadores() {
        Either<ApiError, List<Trabajador>> resultado;
        try {
            Response<List<Trabajador>> response = primeraAPI.getAllTrabajadores().execute();
            if (response.isSuccessful()) {
                resultado = Either.right(response.body());
            } else {
                resultado = Either.left(new ApiError(LocalDate.now(), response.errorBody().string()));
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(new ApiError(LocalDate.now(), e.getMessage()));
        }
        return resultado;
    }

    public Either<ApiError, Trabajador> getTrabajadorId(int id) {
        Either<ApiError, Trabajador> resultado;
        try {
            Response<Trabajador> response = primeraAPI.getTrabajadorId(id).execute();
            if (response.isSuccessful()) {
                resultado = Either.right(response.body());
            } else {
                resultado = Either.left(new ApiError(LocalDate.now(), response.errorBody().string()));
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(new ApiError(LocalDate.now(), e.getMessage()));
        }
        return resultado;
    }

    public Either<ApiError, List<Trabajador>> getAllTrabajadoresPorIdEmpresa(int idEmpresa) {
        Either<ApiError, List<Trabajador>> resultado;
        try {
            Response<List<Trabajador>> response = primeraAPI.getTrabajadorPorIdEmpresa(idEmpresa).execute();
            if (response.isSuccessful()) {
                resultado = Either.right(response.body());
            } else {
                resultado = Either.left(new ApiError(LocalDate.now(), response.errorBody().string()));
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(new ApiError(LocalDate.now(), e.getMessage()));
        }
        return resultado;
    }

    public Either<ApiError, Trabajador> postTrabajador(Trabajador t) {
        Either<ApiError, Trabajador> resultado;
        try {
            Response<Trabajador> response = primeraAPI.postTrabajador(t).execute();
            if (response.isSuccessful()) {
                resultado = Either.right(response.body());
            } else {
                resultado = Either.left(new ApiError(LocalDate.now(), response.errorBody().string()));
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(new ApiError(LocalDate.now(), e.getMessage()));
        }
        return resultado;
    }

    public Either<ApiError, Boolean> deleteTrabajador(int id) {
        Either<ApiError, Boolean> resultado;
        try {
            Response<Boolean> response = primeraAPI.deleteTrabajador(id).execute();
            if (response.isSuccessful()) {
                resultado = Either.right(response.body());
            } else {
                resultado = Either.left(new ApiError(LocalDate.now(), response.errorBody().string()));
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(new ApiError(LocalDate.now(), e.getMessage()));
        }
        return resultado;
    }

    public Either<ApiError, Trabajador> putTrabajador(Trabajador t) {
        Either<ApiError, Trabajador> resultado;
        try {
            Response<Trabajador> response = primeraAPI.putTrabajador(t).execute();
            if (response.isSuccessful()) {
                resultado = Either.right(response.body());
            } else {
                resultado = Either.left(new ApiError(LocalDate.now(), response.errorBody().string()));
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(new ApiError(LocalDate.now(), e.getMessage()));
        }
        return resultado;
    }
}
