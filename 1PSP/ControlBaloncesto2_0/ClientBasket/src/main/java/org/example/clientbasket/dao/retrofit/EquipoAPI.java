package org.example.clientbasket.dao.retrofit;


import io.reactivex.rxjava3.core.Single;
import org.example.clientbasket.utils.Constantes;
import org.example.common.modelo.Equipo;
import retrofit2.http.*;

import java.util.List;

public interface EquipoAPI {

    //getAllEquipos
    @GET(Constantes.EQUIPO)
    Single<List<Equipo>> getAllEquipos();

    @POST(Constantes.EQUIPO)
    Single<Equipo> addEquipo(@Body Equipo equipo);

    @DELETE(Constantes.EQUIPO)
    Single<Equipo> deleteEquipo(@Query(Constantes.NOMBRE_EQUIPO) String nombreEquipo);

    @PUT(Constantes.EQUIPO)
    Single<Equipo> updateEquipo(@Body Equipo equipo, @Query(Constantes.NOMBRE_EQUIPO_NUEVO) String nombreEquipoNuevo);
}
