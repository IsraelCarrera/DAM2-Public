package dao;

import dao.data.*;
import dao.retrofit.TheMovieDBAPI;
import dao.utils.ProducesNetworks;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import modelos.CreditosEpisodio;
import modelos.Serie;
import modelos.TemporadaTV;
import retrofit2.Response;
import utils.Constantes;
import utils.ProducesMapper;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;


@Log4j2
public class DaoSeries implements DAOSer {

    private final ProducesNetworks producesNetworks;
    private final ProducesMapper pm = new ProducesMapper();

    @Inject
    public DaoSeries(ProducesNetworks producesNetworks) {
        this.producesNetworks = producesNetworks;
    }

    @Override
    public Either<String, Serie> getSerieId(int id) {
        Either<String,Serie> resultado = null;
        TheMovieDBAPI consulta = producesNetworks.getRetrofit().create(TheMovieDBAPI.class);
        try {
            Response<SerieTvData> response = consulta.getSerieId(id, Constantes.ES_ES).execute();
            if (response.isSuccessful()) {
                resultado =(Either.right(pm.mapearSerie(response.body())));
            } else {
                resultado=Either.left(response.errorBody().toString());
            }
        } catch (IOException ex) {
            log.error(ex.getMessage(),ex);
        }
        return resultado;
    }

    @Override
    public Either<String, TemporadaTV> getTemporada(int idSerie, int numTemporada) {
        Either<String,TemporadaTV> resultado = null;
        TheMovieDBAPI consulta = producesNetworks.getRetrofit().create(TheMovieDBAPI.class);
        try {
            Response<TemporadaTVData> response = consulta.getTemporada(idSerie, numTemporada, Constantes.ES_ES).execute();
            if (response.isSuccessful()) {
                if(!response.body().getEpisodes().isEmpty()) {
                    resultado = Either.right(pm.mapearTemporada(response.body()));
                }else{
                    resultado = Either.left(Constantes.ERROR_LECTURA_EPISODIO);
                }
            } else {
                resultado= Either.left(response.errorBody().toString());
            }
        } catch (IOException ex) {
            log.error(ex.getMessage(),ex);
        }
        return resultado;
    }

    @Override
    public Either<String, CreditosEpisodio> getCreditosEpisodio(int idSerie, int numeroTemporada, int numeroCapitulo) {
        Either<String, CreditosEpisodio> resultado = null;
        TheMovieDBAPI consulta = producesNetworks.getRetrofit().create(TheMovieDBAPI.class);
        try {
            Response<CreditosEpisodioData> response = consulta.getCreditosEpisodio(idSerie, numeroTemporada, numeroCapitulo, Constantes.ES_ES).execute();
            if (response.isSuccessful()) {
                if(!response.body().getCast().isEmpty()) {
                    resultado = Either.right(pm.mapearCreditosEpisodio(response.body()));
                }else{
                    resultado=Either.left(Constantes.EPISODIO_SIN_INFORMACION);
                }
            } else {
                resultado = Either.left(response.errorBody().toString());
            }
        } catch (IOException ex) {
            log.error(ex.getMessage(),ex);
        }
        return resultado;
    }

    @Override
    public Either<String,List<Serie>> buscarSeriesPorNombre(String nombre) {
        Either<String,List<Serie>> resultado = null;
        TheMovieDBAPI consulta = producesNetworks.getRetrofit().create(TheMovieDBAPI.class);
        try {
            Response<BuscarSerieData> response = consulta.buscarSeries(nombre, Constantes.ES_ES).execute();
            if (response.isSuccessful()) {
                if(!response.body().getResults().isEmpty()) {
                    resultado = Either.right(pm.mapearListSerie(response.body()).getSeries());
                }else{
                    resultado = Either.left(Constantes.NO_EXISTE_SERIE);
                }
            } else {
                resultado = Either.left(response.errorBody().toString());
            }
        } catch (IOException ex) {
            log.error(ex.getMessage(),ex);
        }
        return resultado;
    }
}
