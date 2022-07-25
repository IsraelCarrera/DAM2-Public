package org.example.clientserie.servicios;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import org.example.clientserie.dao.DaoSeries;
import org.example.common.modelo.Capitulo;
import org.example.common.modelo.Serie;

import java.util.List;

public class SeriesServicios {
    private final DaoSeries dao;

    public SeriesServicios() {
        this.dao = new DaoSeries();
    }

    public Single<Either<String, Serie>> addSerie(Serie serie) {
        return dao.addSerie(serie);
    }

    public Single<Either<String, Boolean>> deleteSerie(int idUser, int idSerie) {
        return dao.deleteSerie(idUser, idSerie);
    }

    public Single<Either<String, Capitulo>> addCapitulo(Capitulo capitulo) {
        return dao.addCapitulo(capitulo);
    }

    public Single<Either<String, Boolean>> deleteCapitulo(int idUser, int idSerie, int idCapitulo) {
        return dao.deleteCapitulo(idUser, idSerie, idCapitulo);
    }

    public Single<Either<String, Boolean>> updateCapitulo(int idUser, int idSerie, int idCapitulo, boolean quePaso) {
        return dao.updateCapitulo(idUser, idSerie, idCapitulo, quePaso);
    }

    public Single<Either<String, List<Serie>>> getAllSeries(int id){
        return dao.getAllSeries(id);
    }

    public Single<Either<String, Serie>> getOneSerie(int idUser, int idSerie){
        return dao.getOneSerie(idUser, idSerie);
    }
}
