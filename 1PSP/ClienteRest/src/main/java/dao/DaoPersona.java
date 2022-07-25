package dao;

import dao.modelos.BuscarPersona;
import dao.modelos.PersonaSacadaDeBuscar;
import dao.modelos.Persona;
import dao.retrofit.TheMovieDBAPI;
import dao.utils.ConfigurationSingletonMovies;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import retrofit2.Response;
import utils.Constantes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Log4j2
public class DaoPersona {

    public Either<String,Persona> getPersonaId(int id) {
        Either<String,Persona> resultado = null;
        TheMovieDBAPI consulta = ConfigurationSingletonMovies.getInstance().create(TheMovieDBAPI.class);
        try{
            Response<Persona> response = consulta.getPersonaId(id,Constantes.ES_ES).execute();
            if(response.isSuccessful()){
                resultado = Either.right(response.body());
            }else{
                resultado = Either.left(response.errorBody().toString());
            }
        }catch (IOException ex){
            log.error(ex.getMessage(),ex);
        }
        return resultado;
    }

    public Either<String, List<PersonaSacadaDeBuscar>>  buscarPersonas(String nombre){
        Either<String, List<PersonaSacadaDeBuscar>> resultado = null;
        List<PersonaSacadaDeBuscar> personas= new ArrayList<>();
        TheMovieDBAPI consulta = ConfigurationSingletonMovies.getInstance().create(TheMovieDBAPI.class);
        try{
            Response<BuscarPersona> response = consulta.buscarPersonas(nombre,Constantes.ES_ES).execute();
            if(response.isSuccessful()){
                resultado = Either.right(response.body().getResults());
            }else{
                resultado = Either.left(response.body().toString());
            }
        }catch (IOException ex){
            log.error(ex.getMessage(),ex);
        }

        return resultado;
    }
}
