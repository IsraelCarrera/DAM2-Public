package dao;

import dao.data.BuscarPersonaData;
import dao.data.PersonaData;
import dao.retrofit.TheMovieDBAPI;
import dao.utils.ProducesNetworks;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import modelos.Persona;
import retrofit2.Response;
import utils.Constantes;
import utils.ProducesMapper;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

@Log4j2
public class DaoPersona implements DAOPers{

    private final ProducesNetworks producesNetworks;
    private final ProducesMapper pm = new ProducesMapper();

    @Inject
    public DaoPersona(ProducesNetworks producesNetworks) {
        this.producesNetworks = producesNetworks;
    }

    @Override
    public Either<String, Persona> getPersonaId(int id) {
        Either<String, Persona> resultado = null;
        TheMovieDBAPI consulta = producesNetworks.getRetrofit().create(TheMovieDBAPI.class);
        try{
            Response<PersonaData> response = consulta.getPersonaId(id,Constantes.ES_ES).execute();
            if(response.isSuccessful()){
                resultado = Either.right(pm.mapearPersona(response.body()));
            }else{
                resultado = Either.left(response.errorBody().toString());
            }
        }catch (IOException ex){
            log.error(ex.getMessage(),ex);
        }
        return resultado;
    }

    @Override
    public Either<String, List<Persona>>  buscarPersonas(String nombre){
        Either<String, List<Persona>> resultado = null;
        TheMovieDBAPI consulta = producesNetworks.getRetrofit().create(TheMovieDBAPI.class);
        try{
            Response<BuscarPersonaData> response = consulta.buscarPersonas(nombre,Constantes.ES_ES).execute();
            if(response.isSuccessful()){
                if(!response.body().getResults().isEmpty()) {
                    resultado = Either.right(pm.mapearListPersonas(response.body()).getPersonas());
                }else{
                    resultado = Either.left(Constantes.NO_PERSONA_EXISTE);
                }
            }else{
                resultado = Either.left(response.body().toString());
            }
        }catch (IOException ex){
            log.error(ex.getMessage(),ex);
        }

        return resultado;
    }
}
