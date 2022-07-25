package org.example.clientbasket.dao.retrofit;


import io.reactivex.rxjava3.core.Single;
import org.example.clientbasket.utils.Constantes;
import org.example.common.modelo.Partido;
import retrofit2.http.*;

import java.util.List;

public interface PartidoAPI {

    @GET(Constantes.PARTIDO)
    Single<List<Partido>> getAllPartidos();

    @GET(Constantes.PARTIDO_PARTIDOS_JORNADA)
    Single<List<Partido>> getAllPartidosPorJornada(@Query(Constantes.ID_JORNADA) int idJornada);

    @GET(Constantes.PARTIDO_PARTIDOS_NOMBRE)
    Single<List<Partido>> getAllPartidosPorEquipo(@Query(Constantes.NOMBRE_EQUIPO) String nombreEquipo);

    @POST(Constantes.PARTIDO)
    Single<Partido> addPartido(@Body Partido partido);

    @DELETE(Constantes.PARTIDO)
    Single<Partido> deletePartido(@Query(Constantes.ID_JORNADA) int idJornada, @Query(Constantes.NOMBRE_EQUIPO_LOCAL) String nombreEquipoLocal,
                                @Query(Constantes.NOMBRE_EQUIPO_VISITANTE) String nombreEquipoVisitante);

    @PUT(Constantes.PARTIDO)
    Single<Partido> updatePartido(@Body Partido partido, @Query(Constantes.PUNTOS_EQUIPO_LOCAL) int puntosEquipoLocal,
                                @Query(Constantes.PUNTOS_EQUIPO_VISITANTE) int puntosEquipoVisitante);
}
