package org.example.serverbasket.servicios;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.example.common.error.ApiError;
import org.example.common.modelo.Partido;
import org.example.serverbasket.dao.DaoPartido;

import java.util.List;

public class ServiciosPartido {
    private final DaoPartido dao;

    @Inject
    public ServiciosPartido(DaoPartido dao) {
        this.dao = dao;
    }

    public Either<ApiError, List<Partido>> getAllPartidos(){
        return dao.getAllPartidos();
    }

    public Either<ApiError, List<Partido>> getPartidosPorJornada(int idJornada){
        return dao.getPartidosPorJornada(idJornada);
    }

    public Either<ApiError, List<Partido>> getPartidosPorEquipo(String nombreEquipo){
        return dao.getPartidosPorEquipo(nombreEquipo);
    }

    public Either<ApiError, Partido> addPartido(Partido partido){
        return dao.addPartido(partido);
    }

    public Either<ApiError, Partido> deletePartido(Partido partido){
        return dao.deletePartido(partido);
    }

    public Either<ApiError, Partido> updatePartido(Partido partido, String puntuacion){
        return dao.updatePartido(partido, puntuacion);
    }
}
