package org.example.clientbasket.dao;

import com.google.gson.Gson;
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

    public Either<String, List<Jornada>> getAllJornadas() {
        return this.construirEither(jornadaAPI.getAllJornadas());
    }

    public Either<String, Jornada> addJornada(Jornada jornada) {
        return this.construirEither(jornadaAPI.addJornada(jornada));
    }

    public Either<String, Integer> deleteJornada(int idJornada) {
        return this.construirEither(jornadaAPI.deleteJornada(idJornada));
    }

    public Either<String, Jornada> updateJornada(Jornada jornada, LocalDate nuevaFecha) {
        return this.construirEither(jornadaAPI.updateJornada(jornada, nuevaFecha.toString()));
    }
}
