package org.example.clientserie.dao;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import org.example.clientserie.dao.retrofit.SeriesAPI;
import org.example.clientserie.dao.retrofit.UsuarioAPI;
import org.example.clientserie.dao.utils.Producers;
import org.example.common.modelo.Capitulo;
import org.example.common.modelo.Serie;

import java.util.List;

public class DaoSeries extends DaoGenerico{
    public Single<Either<String, Serie>> addSerie(Serie serie){
        SeriesAPI consulta = Producers.getInstance().create(SeriesAPI.class);
        return this.construirSingle(consulta.addSerie(serie));
    }
    public Single<Either<String, Boolean>> deleteSerie(int idUser, int idSerie){
        SeriesAPI consulta = Producers.getInstance().create(SeriesAPI.class);
        return this.construirSingle(consulta.deleteSerie(idUser,idSerie));
    }
    public Single<Either<String, Capitulo>> addCapitulo(Capitulo capitulo){
        SeriesAPI consulta = Producers.getInstance().create(SeriesAPI.class);
        return this.construirSingle(consulta.addCapitulo(capitulo));
    }
    public Single<Either<String, Boolean>> deleteCapitulo(int idUser, int idSerie, int idCapitulo){
        SeriesAPI consulta = Producers.getInstance().create(SeriesAPI.class);
        return this.construirSingle(consulta.deleteCapitulo(idUser, idSerie, idCapitulo));
    }
    public Single<Either<String, Boolean>> updateCapitulo(int idUser, int idSerie, int idCapitulo, boolean quePaso){
        SeriesAPI consulta = Producers.getInstance().create(SeriesAPI.class);
        return this.construirSingle(consulta.updateCapitulo(idUser, idSerie, idCapitulo, quePaso));
    }
    public Single<Either<String, List<Serie>>> getAllSeries(int id){
        SeriesAPI consulta = Producers.getInstance().create(SeriesAPI.class);
        return this.construirSingle(consulta.getAllSeries(id));
    }
    public Single<Either<String, Serie>> getOneSerie(int idUser, int idSerie){
        SeriesAPI consulta = Producers.getInstance().create(SeriesAPI.class);
        return this.construirSingle(consulta.getOneSerie(idUser, idSerie));
    }

}
