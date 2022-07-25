package dao.retrofit;

import dao.data.PersonaData;
import retrofit2.Call;
import retrofit2.http.*;
import utils.Constantes;

import java.util.List;

public interface PersonasAPI {

    @GET(Constantes.PERSONAS)
    Call<List<PersonaData>> getAllPersonas();

    @POST(Constantes.PERSONAS)
    Call<PersonaData> addPersona(@Body PersonaData p);

    @DELETE(Constantes.PERSONAS)
    Call<List<PersonaData>> deletePersona(@Query(Constantes.ID) String id);

    @PUT(Constantes.PERSONAS)
    Call<PersonaData> updatePersona(@Body PersonaData p);

    @GET("personas/filtrar/")
    Call<List<PersonaData>> getPorFiltro(@Query(Constantes.LUGAR_PROCEDENCIA) String lugarProcedencia, @Query(Constantes.ANO_NACIMIENTO) int anoNacimiento,
                                         @Query(Constantes.N_HIJOS) int nHijos, @Query(Constantes.ESTADO_CIVIL) String estadoCivil);
}
