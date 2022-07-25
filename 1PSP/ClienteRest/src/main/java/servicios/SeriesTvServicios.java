package servicios;

import dao.DaoSeries;
import dao.modelos.CreditosEpisodio;
import dao.modelos.SerieSacadaDeBuscar;
import dao.modelos.SerieTv;
import dao.modelos.TemporadaTV;

import java.util.List;

public class SeriesTvServicios {
    public SerieTv seriePorId(int id) {
        DaoSeries dao = new DaoSeries();
        return dao.getSerieId(id);
    }

    public TemporadaTV getTemporada(int idSerie, int numTemporada) {
        DaoSeries dao = new DaoSeries();
        return dao.getTemporada(idSerie, numTemporada);
    }

    public CreditosEpisodio getEpisodio(int idSerie, int numeroTemporada, int numeroCapitulo) {
        DaoSeries dao = new DaoSeries();
        return dao.getCreditosEpisodio(idSerie, numeroTemporada, numeroCapitulo);
    }

    public List<SerieSacadaDeBuscar> buscarSeries(String nombre){
        DaoSeries dao = new DaoSeries();
        return dao.buscarSeriesPorNombre(nombre);
    }
}
