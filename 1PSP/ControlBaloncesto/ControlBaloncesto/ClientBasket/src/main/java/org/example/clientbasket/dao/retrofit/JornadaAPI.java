package org.example.clientbasket.dao.retrofit;


import org.example.clientbasket.utils.Constantes;
import org.example.common.modelo.Jornada;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface JornadaAPI {

    @GET(Constantes.JORNADA)
    Call<List<Jornada>> getAllJornadas();

    @POST(Constantes.JORNADA)
    Call<Jornada> addJornada(@Body Jornada jornada);

    @DELETE(Constantes.JORNADA)
    Call<Integer> deleteJornada(@Query(Constantes.ID_JORNADA) int idJornada);

    @PUT(Constantes.JORNADA)
    Call<Jornada> updateJornada(@Body Jornada jornada, @Query(Constantes.NUEVA_FECHA) String nuevaFecha);
}
