package org.example.clientbasket.dao.retrofit;


import org.example.clientbasket.utils.Constantes;
import org.example.common.modelo.Partido;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface PartidoAPI {

    @GET(Constantes.PARTIDO)
    Call<List<Partido>> getAllPartidos();

    @GET(Constantes.PARTIDO_PARTIDOS_JORNADA)
    Call<List<Partido>> getAllPartidosPorJornada(@Query(Constantes.ID_JORNADA) int idJornada);

    @GET(Constantes.PARTIDO_PARTIDOS_NOMBRE)
    Call<List<Partido>> getAllPartidosPorEquipo(@Query(Constantes.NOMBRE_EQUIPO) String nombreEquipo);

    @POST(Constantes.PARTIDO)
    Call<Partido> addPartido(@Body Partido partido);

    @DELETE(Constantes.PARTIDO)
    Call<Partido> deletePartido(@Query(Constantes.ID_JORNADA) int idJornada, @Query(Constantes.NOMBRE_EQUIPO_LOCAL) String nombreEquipoLocal,
                                @Query(Constantes.NOMBRE_EQUIPO_VISITANTE) String nombreEquipoVisitante);

    @PUT(Constantes.PARTIDO)
    Call<Partido> updatePartido(@Body Partido partido, @Query(Constantes.PUNTOS_EQUIPO_LOCAL) int puntosEquipoLocal,
                                @Query(Constantes.PUNTOS_EQUIPO_VISITANTE) int puntosEquipoVisitante);
}
