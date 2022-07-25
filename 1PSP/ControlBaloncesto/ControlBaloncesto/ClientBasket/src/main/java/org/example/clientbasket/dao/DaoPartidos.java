package org.example.clientbasket.dao;

import com.google.gson.Gson;
import io.vavr.control.Either;
import org.example.clientbasket.dao.retrofit.PartidoAPI;
import org.example.common.modelo.Partido;

import javax.inject.Inject;
import java.util.List;

public class DaoPartidos extends DaoGenerico {
    private final PartidoAPI partidoAPI;

    @Inject
    public DaoPartidos(Gson g, PartidoAPI partidoAPI) {
        super(g);
        this.partidoAPI = partidoAPI;
    }

    public Either<String, List<Partido>> getAllPartidos() {
        return this.construirEither(partidoAPI.getAllPartidos());
    }

    public Either<String, List<Partido>> getAllPartidosPorJornada(int idJornada) {
        return this.construirEither(partidoAPI.getAllPartidosPorJornada(idJornada));
    }

    public Either<String, List<Partido>> getAllPartidosPorEquipo(String nombreEquipo) {
        return this.construirEither(partidoAPI.getAllPartidosPorEquipo(nombreEquipo));
    }

    public Either<String, Partido> addPartido(Partido partido) {
        return this.construirEither(partidoAPI.addPartido(partido));
    }

    public Either<String, Partido> deletePartido(int idJornada, String nombreEquipoLocal, String nombreEquipoVisitante) {
        return this.construirEither(partidoAPI.deletePartido(idJornada, nombreEquipoLocal, nombreEquipoVisitante));
    }

    public Either<String, Partido> updatePartido(Partido partido, int puntosEquipoLocal, int puntosEquipoVisitante) {
        return this.construirEither(partidoAPI.updatePartido(partido, puntosEquipoLocal, puntosEquipoVisitante));
    }
}
