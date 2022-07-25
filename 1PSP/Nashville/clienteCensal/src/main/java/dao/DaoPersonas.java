package dao;

import com.google.gson.Gson;
import dao.data.PersonaData;
import dao.retrofit.DefuncionesAPI;
import dao.retrofit.EmparejarAPI;
import dao.retrofit.NacimientosAPI;
import dao.retrofit.PersonasAPI;
import error.ApiError;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import retrofit2.Response;
import utils.Constantes;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

@Log4j2
public class DaoPersonas {
    private final DefuncionesAPI defuncionesAPI;
    private final EmparejarAPI emparejarAPI;
    private final NacimientosAPI nacimientosAPI;
    private final PersonasAPI personasAPI;
    private final Gson gson;

    @Inject
    public DaoPersonas(DefuncionesAPI defuncionesAPI, EmparejarAPI emparejarAPI, NacimientosAPI nacimientosAPI, PersonasAPI personasAPI, Gson gson) {
        this.defuncionesAPI = defuncionesAPI;
        this.emparejarAPI = emparejarAPI;
        this.nacimientosAPI = nacimientosAPI;
        this.personasAPI = personasAPI;
        this.gson = gson;
    }

    public Either<String, List<PersonaData>> getAllPersonas() {
        Either<String, List<PersonaData>> resultado;
        try {
            Response<List<PersonaData>> response = personasAPI.getAllPersonas().execute();
            if (response.isSuccessful()) {
                resultado = Either.right(response.body());
            } else {
                ApiError apiError = gson.fromJson(response.errorBody().string(), ApiError.class);
                resultado = Either.left(apiError.getMensaje());
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Constantes.FALLO_AL_CONECTAR_CON_EL_SERVIDOR);
        }
        return resultado;
    }

    public Either<String, PersonaData> vieneUnaPersona(PersonaData p) {
        Either<String, PersonaData> resultado;
        try {
            Response<PersonaData> response = personasAPI.addPersona(p).execute();
            if (response.isSuccessful()) {
                resultado = Either.right(response.body());
            } else {
                ApiError apiError = gson.fromJson(response.errorBody().string(), ApiError.class);
                resultado = Either.left(apiError.getMensaje());
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Constantes.FALLO_AL_CONECTAR_CON_EL_SERVIDOR);
        }
        return resultado;
    }

    public Either<String, List<PersonaData>> seMudaFueraUnDesgraciado(String id) {
        Either<String, List<PersonaData>> resultado;
        try {
            Response<List<PersonaData>> response = personasAPI.deletePersona(id).execute();
            if (response.isSuccessful()) {
                resultado = Either.right(response.body());
            } else {
                ApiError apiError = gson.fromJson(response.errorBody().string(), ApiError.class);
                resultado = Either.left(apiError.getMensaje());
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Constantes.FALLO_AL_CONECTAR_CON_EL_SERVIDOR);
        }

        return resultado;
    }

    public Either<String, PersonaData> updatePersona(PersonaData p) {
        Either<String, PersonaData> resultado;
        try {
            Response<PersonaData> response = personasAPI.updatePersona(p).execute();
            if (response.isSuccessful()) {
                resultado = Either.right(response.body());
            } else {
                ApiError apiError = gson.fromJson(response.errorBody().string(), ApiError.class);
                resultado = Either.left(apiError.getMensaje());
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Constantes.FALLO_AL_CONECTAR_CON_EL_SERVIDOR);
        }
        return resultado;
    }

    public Either<String, List<PersonaData>> getPersonasFiltros(String lugarProcedencia, int anoNacimiento, int nHijos, String estadoCivil) {
        Either<String, List<PersonaData>> resultado;
        try {
            Response<List<PersonaData>> response = personasAPI.getPorFiltro(lugarProcedencia, anoNacimiento, nHijos, estadoCivil).execute();
            if (response.isSuccessful()) {
                resultado = Either.right(response.body());
            } else {
                ApiError apiError = gson.fromJson(response.errorBody().string(), ApiError.class);
                resultado = Either.left(apiError.getMensaje());
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Constantes.FALLO_AL_CONECTAR_CON_EL_SERVIDOR);
        }
        return resultado;
    }

    public Either<String, List<PersonaData>> getPersonasPorSexo(String sexo) {
        Either<String, List<PersonaData>> resultado;
        try {
            Response<List<PersonaData>> response = emparejarAPI.getAllPersonas(sexo).execute();
            if (response.isSuccessful()) {
                resultado = Either.right(response.body());
            } else {
                ApiError apiError = gson.fromJson(response.errorBody().string(), ApiError.class);
                resultado = Either.left(apiError.getMensaje());
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Constantes.FALLO_AL_CONECTAR_CON_EL_SERVIDOR);
        }
        return resultado;
    }

    public Either<String, Boolean> casarPersonas(String idHombre, String idMujer) {
        Either<String, Boolean> resultado;
        try {
            Response<Boolean> response = emparejarAPI.hacerEmparejamiento(idHombre, idMujer).execute();
            if (response.isSuccessful()) {
                resultado = Either.right(response.body());
            } else {
                ApiError apiError = gson.fromJson(response.errorBody().string(), ApiError.class);
                resultado = Either.left(apiError.getMensaje());
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Constantes.FALLO_AL_CONECTAR_CON_EL_SERVIDOR);
        }
        return resultado;
    }

    public Either<Integer, Integer> matarPersona(String idPersona) {
        Either<Integer, Integer> resultado;
        try {
            Response<Integer> response = defuncionesAPI.matarAPersona(idPersona).execute();
            if (response.isSuccessful()) {
                resultado = Either.right(response.body());
            } else if (response.code() == 489) {
                resultado = Either.left(489);
            } else {
                resultado = Either.left(488);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(487);
        }
        return resultado;
    }

    public Either<Integer, PersonaData> registrarBebe(String idPadre, String idMadre, PersonaData p) {
        Either<Integer, PersonaData> resultado;
        try {
            Response<PersonaData> response = nacimientosAPI.registrarNacimiento(idPadre, idMadre, p).execute();
            if (response.isSuccessful()) {
                resultado = Either.right(response.body());
            } else if (response.code() == 499) {
                resultado = Either.left(499);
            } else {
                resultado = Either.left(498);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(497);
        }
        return resultado;
    }

    public Either<String, Boolean> promiscuosFuera(String idP1, String idP2) {
        Either<String, Boolean> resultado;
        try {
            Response<Void> response = nacimientosAPI.deletearPromiscuos(idP1, idP2).execute();
            if (response.isSuccessful()) {
                resultado = Either.right(true);
            } else {
                ApiError apiError = gson.fromJson(response.errorBody().string(), ApiError.class);
                resultado = Either.left(apiError.getMensaje());
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            resultado = Either.left(Constantes.FALLO_AL_CONECTAR_CON_EL_SERVIDOR);
        }
        return resultado;
    }
}
