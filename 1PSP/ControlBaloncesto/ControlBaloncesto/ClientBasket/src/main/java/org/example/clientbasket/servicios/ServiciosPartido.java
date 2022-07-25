package org.example.clientbasket.servicios;

import io.vavr.control.Either;
import org.example.clientbasket.dao.DaoPartidos;
import org.example.common.modelo.Partido;

import javax.inject.Inject;
import java.util.List;

public class ServiciosPartido {
    private final DaoPartidos dao;

    @Inject
    public ServiciosPartido(DaoPartidos dao) {
        this.dao = dao;
    }

    public Either<String, List<Partido>> getAllPartidos() {
        return dao.getAllPartidos();
    }

    public Either<String, List<Partido>> getAllPartidosPorJornada(int idJornada) {
        return dao.getAllPartidosPorJornada(idJornada);
    }

    public Either<String, List<Partido>> getAllPartidosPorEquipo(String nombreEquipo) {
        return dao.getAllPartidosPorEquipo(nombreEquipo);
    }

    public Either<String, Partido> addPartido(Partido partido) {
        return dao.addPartido(partido);
    }

    public Either<String, Partido> deletePartido(int idJornada, String nombreEquipoLocal, String nombreEquipoVisitante) {
        return dao.deletePartido(idJornada, nombreEquipoLocal, nombreEquipoVisitante);
    }

    public Either<String, Partido> updatePartido(Partido partido, int puntosEquipoLocal, int puntosEquipoVisitante) {
        return dao.updatePartido(partido, puntosEquipoLocal, puntosEquipoVisitante);
    }
}
