package org.example.clientbasket.dao;

import com.google.gson.Gson;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import org.example.clientbasket.dao.retrofit.JornadaAPI;
import org.example.common.modelo.Jornada;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;

public class DaoJornadas extends DaoGenerico {
    private final JornadaAPI jornadaAPI;

    @Inject
    public DaoJornadas(Gson g, JornadaAPI jornadaAPI) {
        super(g);
        this.jornadaAPI = jornadaAPI;
    }

    public Single<Either<String, List<Jornada>>> getAllJornadas() {
        return this.construirSingle(jornadaAPI.getAllJornadas());
    }

    public Single<Either<String, Jornada>> addJornada(Jornada jornada) {
        return this.construirSingle(jornadaAPI.addJornada(jornada));
    }

    public Single<Either<String, Integer>> deleteJornada(int idJornada) {
        return this.construirSingle(jornadaAPI.deleteJornada(idJornada));
    }

    public Single<Either<String, Jornada>> updateJornada(Jornada jornada, LocalDate nuevaFecha) {
        return this.construirSingle(jornadaAPI.updateJornada(jornada, nuevaFecha.toString()));
    }
}
