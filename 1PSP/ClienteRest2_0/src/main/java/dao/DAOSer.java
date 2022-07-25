package dao;


import io.vavr.control.Either;
import modelos.CreditosEpisodio;
import modelos.Serie;
import modelos.TemporadaTV;

import java.util.List;

public interface DAOSer {
    Either<String, Serie> getSerieId(int id);

    Either<String, TemporadaTV> getTemporada(int idSerie, int numTemporada);

    Either<String, CreditosEpisodio> getCreditosEpisodio(int idSerie, int numeroTemporada, int numeroCapitulo);

    Either<String, List<Serie>> buscarSeriesPorNombre(String nombre);

}
