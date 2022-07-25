package dao;

import dao.modelos.*;
import dao.retrofit.TheMovieDBAPI;
import dao.utils.ConfigurationSingletonMovies;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import retrofit2.Response;
import utils.Constantes;
import java.io.IOException;
import java.util.List;

@Log4j2
public class DaoPelicula {
    public Either<String, Pelicula> getPeliculaId(int id) {
        Either<String, Pelicula> resultado = null;
        TheMovieDBAPI consulta = ConfigurationSingletonMovies.getInstance().create(TheMovieDBAPI.class);
        try {
            Response<Pelicula> response = consulta.getPeliculaId(id,Constantes.ES_ES).execute();
            if (response.isSuccessful()) {
                resultado = Either.right(response.body());
            } else {
                resultado = Either.left(response.errorBody().toString());
            }
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
        }
        return resultado;
    }

    public Either<String,PeliculasPopulares> peliculasPopulares(int pag) {
        Either<String, PeliculasPopulares> resultado = null;
        TheMovieDBAPI consulta = ConfigurationSingletonMovies.getInstance().create(TheMovieDBAPI.class);
        try {
            Response<PeliculasPopulares> response = consulta.getPeliculasPopulares(pag,Constantes.ES_ES, "ES").execute();
            if (response.isSuccessful()) {
                resultado = Either.right(response.body());
            } else {
                resultado = Either.left(response.errorBody().toString());
            }
        } catch (IOException ex) {
            log.error(ex.getMessage(),ex);
        }
        return resultado;
    }

    public Either<String,List<Pelicula>>  getColecciones(int id) {
        Either<String,List<Pelicula>> resultado = null;
        TheMovieDBAPI consulta = ConfigurationSingletonMovies.getInstance().create(TheMovieDBAPI.class);
        try {
            Response<Colecciones> response = consulta.getColecciones(id, Constantes.ES_ES).execute();
            if (response.isSuccessful()) {
                resultado = Either.right(response.body().getParts());
            } else {
                resultado = Either.left(response.errorBody().toString());
            }
        } catch (IOException ex) {
            log.error(ex.getMessage(),ex);
        }
        return resultado;
    }

    public Either <String,CastPelicula> getCastPeli(int id) {
        Either <String,CastPelicula> resultado = null;
        TheMovieDBAPI consulta = ConfigurationSingletonMovies.getInstance().create(TheMovieDBAPI.class);
        try {
            Response<CastPelicula> response = consulta.getCastPelicula(id, Constantes.ES_ES).execute();
            if (response.isSuccessful()) {
                resultado = Either.right(response.body());
            } else {
                resultado = Either.left(response.errorBody().toString());
            }
        } catch (IOException ex) {
            log.error(ex.getMessage(),ex);
        }

        return resultado;
    }

    public Either <String,List<PeliculaSacadaDeBuscar>> buscarPelicula(String name) {
        Either <String,List<PeliculaSacadaDeBuscar>> resultado = null;
        TheMovieDBAPI consulta = ConfigurationSingletonMovies.getInstance().create(TheMovieDBAPI.class);
        try{
            Response<BuscarPelicula> response = consulta.buscarPeliculas(name, Constantes.ES_ES).execute();
            if(response.isSuccessful()){
                resultado = Either.right(response.body().getResults());
            }else{
                resultado= Either.left(response.errorBody().toString());
            }
        }catch (IOException ex){
            log.error(ex.getMessage(),ex);
        }
        return resultado;
    }
}
