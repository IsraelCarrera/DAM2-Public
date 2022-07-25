package org.example.clientbasket.dao.retrofit;


import io.reactivex.rxjava3.core.Single;
import org.example.clientbasket.utils.Constantes;
import org.example.common.modelo.Jornada;
import retrofit2.http.*;

import java.util.List;

public interface JornadaAPI {

    @GET(Constantes.JORNADA)
    Single<List<Jornada>> getAllJornadas();

    @POST(Constantes.JORNADA)
    Single<Jornada> addJornada(@Body Jornada jornada);

    @DELETE(Constantes.JORNADA)
    Single<Integer> deleteJornada(@Query(Constantes.ID_JORNADA) int idJornada);

    @PUT(Constantes.JORNADA)
    Single<Jornada> updateJornada(@Body Jornada jornada, @Query(Constantes.NUEVA_FECHA) String nuevaFecha);
}
