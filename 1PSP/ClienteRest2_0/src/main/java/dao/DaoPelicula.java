package dao;

import dao.data.*;
import dao.retrofit.TheMovieDBAPI;
import dao.utils.ProducesNetworks;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import modelos.CreditosPelicula;
import modelos.Pelicula;
import retrofit2.Response;
import utils.Constantes;
import utils.ProducesMapper;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

@Log4j2
public class DaoPelicula implements DAOPeli {

    private final ProducesNetworks producesNetworks;

    private final ProducesMapper pm = new ProducesMapper();

    @Inject
    public DaoPelicula(ProducesNetworks producesNetworks) {
        this.producesNetworks = producesNetworks;
    }

    @Override
    public Either<String, Pelicula> getPeliculaId(int id) {
        Either<String, Pelicula> resultado = null;
        TheMovieDBAPI consulta = producesNetworks.getRetrofit().create(TheMovieDBAPI.class);
        try {
            Response<PeliculaData> response = consulta.getPeliculaId(id, Constantes.ES_ES).execute();
            if (response.isSuccessful()) {
                resultado = Either.right(pm.mapearPeli(response.body()));
            } else {
                resultado = Either.left(response.errorBody().toString());
            }
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
        }
        return resultado;
    }

    @Override
    public Either<String, List<Pelicula>> peliculasPopulares(int pag) {
        Either<String, List<Pelicula>> resultado = null;
        TheMovieDBAPI consulta = producesNetworks.getRetrofit().create(TheMovieDBAPI.class);
        try {
            Response<PeliculasPopulares> response = consulta.getPeliculasPopulares(pag, Constantes.ES_ES, "ES").execute();
            if (response.isSuccessful()) {
                if (!response.body().getResults().isEmpty()) {
                    resultado = Either.right(pm.mapearListPeliculasPopulares(response.body()).getPeliculas());
                } else {
                    resultado = Either.left(Constantes.NO_EXISTE_PELI);
                }
            } else {
                resultado = Either.left(response.errorBody().toString());
            }
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
        }
        return resultado;
    }

    @Override
    public Either<String, List<Pelicula>> getColecciones(int id) {
        Either<String, List<Pelicula>> resultado = null;
        TheMovieDBAPI consulta = producesNetworks.getRetrofit().create(TheMovieDBAPI.class);
        try {
            Response<ColeccionesData> response = consulta.getColecciones(id, Constantes.ES_ES).execute();
            if (response.isSuccessful()) {
                if (!response.body().getParts().isEmpty()) {
                    resultado = Either.right(pm.mapearListPeliculasColecciones(response.body()).getParts());
                } else {
                    resultado = Either.left(Constantes.NO_EXISTE_PELI);
                }
            } else {
                resultado = Either.left(response.errorBody().toString());
            }
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
        }
        return resultado;
    }

    @Override
    public Either<String, CreditosPelicula> getCastPeli(int id) {
        Either<String, CreditosPelicula> resultado = null;
        TheMovieDBAPI consulta = producesNetworks.getRetrofit().create(TheMovieDBAPI.class);
        try {
            Response<CastPeliculaData> response = consulta.getCastPelicula(id, Constantes.ES_ES).execute();
            if (response.isSuccessful()) {
                resultado = Either.right(pm.mapearPeliculaCreditos(response.body()));
            } else {
                resultado = Either.left(response.errorBody().toString());
            }
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
        }

        return resultado;
    }

    @Override
    public Either<String, List<Pelicula>> buscarPelicula(String name) {
        Either<String, List<Pelicula>> resultado = null;
        TheMovieDBAPI consulta = producesNetworks.getRetrofit().create(TheMovieDBAPI.class);
        try {
            Response<BuscarPeliculaData> response = consulta.buscarPeliculas(name, Constantes.ES_ES).execute();
            if (response.isSuccessful()) {
                if (!response.body().getResults().isEmpty()) {
                    resultado = Either.right(pm.mapearListPeliculas(response.body()).getPeliculas());
                } else {
                    resultado = Either.left(Constantes.NO_EXISTE_PELI);
                }
            } else {
                resultado = Either.left(response.errorBody().toString());
            }
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
        }
        return resultado;
    }

}
