package dao;

import dao.modelos.*;
import dao.retrofit.TheMovieDBAPI;
import dao.utils.ConfigurationSingletonMovies;
import retrofit2.Response;
import utils.Constantes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoSeries {

    public SerieTv getSerieId(int id) {
        TheMovieDBAPI consulta = ConfigurationSingletonMovies.getInstance().create(TheMovieDBAPI.class);
        SerieTv serie = null;
        try {
            Response<SerieTv> response = consulta.getSerieId(id, Constantes.ES_ES).execute();
            if (response.isSuccessful()) {
                serie = response.body();
            } else {
                Logger.getLogger("Error isSucessful de getSerieId").log(Level.INFO, response.errorBody().string());
            }
        } catch (IOException ex) {
            Logger.getLogger("Error seriesID").log(Level.INFO, ex.getMessage(), ex);
        }
        return serie;
    }

    public TemporadaTV getTemporada(int idSerie, int numTemporada) {
        TemporadaTV temporada = null;
        TheMovieDBAPI consulta = ConfigurationSingletonMovies.getInstance().create(TheMovieDBAPI.class);
        try {
            Response<TemporadaTV> response = consulta.getTemporada(idSerie, numTemporada, Constantes.ES_ES).execute();
            if (response.isSuccessful()) {
                temporada = response.body();
            } else {
                Logger.getLogger("Error isSucessful de getTemporada").log(Level.INFO, response.errorBody().string());
            }
        } catch (IOException ex) {
            Logger.getLogger("Error temporada").log(Level.INFO, ex.getMessage(), ex);
        }
        return temporada;
    }

    public CreditosEpisodio getCreditosEpisodio(int idSerie, int numeroTemporada, int numeroCapitulo) {
        CreditosEpisodio ep = null;
        TheMovieDBAPI consulta = ConfigurationSingletonMovies.getInstance().create(TheMovieDBAPI.class);
        try {
            Response<CreditosEpisodio> response = consulta.getCreditosEpisodio(idSerie, numeroTemporada, numeroCapitulo, Constantes.ES_ES).execute();
            if (response.isSuccessful()) {
                ep = response.body();
            } else {
                Logger.getLogger("Error isSucessful de getCreditosEpisodio").log(Level.INFO, response.errorBody().string());
            }
        } catch (IOException ex) {
            Logger.getLogger("Error creditos episodios").log(Level.INFO, ex.getMessage(), ex);
        }
        return ep;
    }

    public List<SerieSacadaDeBuscar> buscarSeriesPorNombre(String nombre) {
        List<SerieSacadaDeBuscar> series = new ArrayList<>();
        TheMovieDBAPI consulta = ConfigurationSingletonMovies.getInstance().create(TheMovieDBAPI.class);
        try {
            Response<BuscarSerie> response = consulta.buscarSeries(nombre, Constantes.ES_ES).execute();
            if (response.isSuccessful()) {
                series = response.body().getResults();
            } else {
                Logger.getLogger("Error isSucessful de buscarSeriesPorNombre").log(Level.INFO, response.errorBody().string());
            }
        } catch (IOException ex) {
            Logger.getLogger("Error buscarSeriePorNombre").log(Level.INFO, ex.getMessage(), ex);
        }
        if (series.isEmpty()) {
            series = null;
        }
        return series;
    }
}
