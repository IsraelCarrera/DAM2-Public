package org.example.clientbasket.dao;

import com.google.gson.Gson;
import io.reactivex.rxjava3.core.Single;
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

    public Single<Either<String, List<Partido>>> getAllPartidos() {
        return this.construirSingle(partidoAPI.getAllPartidos());
    }

    public Single<Either<String, List<Partido>>> getAllPartidosPorJornada(int idJornada) {
        return this.construirSingle(partidoAPI.getAllPartidosPorJornada(idJornada));
    }

    public Single<Either<String, List<Partido>>> getAllPartidosPorEquipo(String nombreEquipo) {
        return this.construirSingle(partidoAPI.getAllPartidosPorEquipo(nombreEquipo));
    }

    public Single<Either<String, Partido>> addPartido(Partido partido) {
        return this.construirSingle(partidoAPI.addPartido(partido));
    }

    public Single<Either<String, Partido>> deletePartido(int idJornada, String nombreEquipoLocal, String nombreEquipoVisitante) {
        return this.construirSingle(partidoAPI.deletePartido(idJornada, nombreEquipoLocal, nombreEquipoVisitante));
    }

    public Single<Either<String, Partido>> updatePartido(Partido partido, int puntosEquipoLocal, int puntosEquipoVisitante) {
        return this.construirSingle(partidoAPI.updatePartido(partido, puntosEquipoLocal, puntosEquipoVisitante));
    }
}
