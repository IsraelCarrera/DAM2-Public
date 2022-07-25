package dao;

import dao.data.ApiError;
import dao.data.Empresa;
import dao.retrofit.PrimeraAPI;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import retrofit2.Response;

import javax.inject.Inject;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Log4j2
public class DaoEmpresa {
    private final PrimeraAPI primeraAPI;

    @Inject
    public DaoEmpresa(PrimeraAPI primeraAPI) {
        this.primeraAPI = primeraAPI;
    }

    public Either<ApiError, List<Empresa>> getAllEmpresas() {
        Either<ApiError, List<Empresa>> resultado;
        try {
            Response<List<Empresa>> response = primeraAPI.getAllEmpresas().execute();
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

    public Either<ApiError, Empresa> getEmpresaId(int id) {
        Either<ApiError, Empresa> resultado;
        try {
            Response<Empresa> response = primeraAPI.getEmpresaId(id).execute();
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

    public Either<ApiError, Empresa> postEmpresa(Empresa empresa) {
        Either<ApiError, Empresa> resultado;
        try {
            Response<Empresa> response = primeraAPI.postEmpresa(empresa).execute();
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

    public Either<ApiError, Boolean> deleteEmpresa(int id) {
        Either<ApiError, Boolean> resultado;
        try {
            Response<Boolean> response = primeraAPI.deleteEmpresa(id).execute();
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

    public Either<ApiError, Empresa> putEmpresa(Empresa empresa) {
        Either<ApiError, Empresa> resultado;
        try {
            Response<Empresa> response = primeraAPI.putEmpresa(empresa).execute();
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
