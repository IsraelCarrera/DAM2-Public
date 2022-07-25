package org.example.serverserie.servicios;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.example.common.modelo.ApiError;
import org.example.common.modelo.Capitulo;
import org.example.common.modelo.Serie;
import org.example.serverserie.dao.DaoUsuarios;
import java.util.List;

public class ServiciosSeries {
    private final DaoUsuarios dao;

    @Inject
    public ServiciosSeries(DaoUsuarios dao) {
        this.dao = dao;
    }

    public Either<ApiError, Serie> addSerie(Serie serie){
        return dao.addSerie(serie);
    }

    public Either<ApiError, Capitulo> addCapitulo(Capitulo capitulo){
        return dao.addCapitulo(capitulo);
    }

    public Either<ApiError, Boolean> updateCapitulo(int idUser, int idSerie, int idCapitulo, boolean quepaso) {
        return dao.updateCapitulo(idUser, idSerie, idCapitulo, quepaso);
    }

    public Either<ApiError, Boolean> borrarSerie(int idUser, int idSerie){
        return dao.borrarSerie(idUser, idSerie);
    }

    public Either<ApiError,Boolean> borrarCapitulo(int idUser, int idSerie, int idCapitulo){
        return dao.borrarCapitulo(idUser, idSerie, idCapitulo);
    }

    public Either<ApiError,Serie> getOneSerie(int idUser, int idSerie){
        return dao.getOneSerie(idUser, idSerie);
    }

    public Either<ApiError, List<Serie>> getAllSeries(int idUser){
        return dao.getAllSeries(idUser);
    }
}

