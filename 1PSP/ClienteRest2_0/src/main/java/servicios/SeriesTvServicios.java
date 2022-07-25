package servicios;

import dao.DAOSer;
import io.vavr.control.Either;
import modelos.CreditosEpisodio;
import modelos.Serie;
import modelos.TemporadaTV;

import javax.inject.Inject;
import java.util.List;

public class SeriesTvServicios {
    DAOSer dao;

    @Inject
    public SeriesTvServicios(DAOSer dao) {
        this.dao = dao;
    }

    public Either<String, Serie> seriePorId(int id) {
        return dao.getSerieId(id);
    }

    public Either<String, TemporadaTV> getTemporada(int idSerie, int numTemporada) {
        return dao.getTemporada(idSerie, numTemporada);
    }

    public Either<String, CreditosEpisodio> getEpisodio(int idSerie, int numeroTemporada, int numeroCapitulo) {
        return dao.getCreditosEpisodio(idSerie, numeroTemporada, numeroCapitulo);
    }

    public Either<String, List<Serie>> buscarSeries(String nombre) {
        return dao.buscarSeriesPorNombre(nombre);
    }
}
