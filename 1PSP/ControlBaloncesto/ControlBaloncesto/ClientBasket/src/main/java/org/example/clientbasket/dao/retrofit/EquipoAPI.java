package org.example.clientbasket.dao.retrofit;


import org.example.clientbasket.utils.Constantes;
import org.example.common.modelo.Equipo;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface EquipoAPI {

    //getAllEquipos
    @GET(Constantes.EQUIPO)
    Call<List<Equipo>> getAllEquipos();

    @POST(Constantes.EQUIPO)
    Call<Equipo> addEquipo(@Body Equipo equipo);

    @DELETE(Constantes.EQUIPO)
    Call<Equipo> deleteEquipo(@Query(Constantes.NOMBRE_EQUIPO) String nombreEquipo);

    @PUT(Constantes.EQUIPO)
    Call<Equipo> updateEquipo(@Body Equipo equipo, @Query(Constantes.NOMBRE_EQUIPO_NUEVO) String nombreEquipoNuevo);
}
