package org.example.serverbasket.EE.rest;


import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.log4j.Log4j2;
import org.example.common.error.ApiError;
import org.example.common.modelo.Partido;
import org.example.serverbasket.EE.filtros.Admin;
import org.example.serverbasket.EE.filtros.UsuarioRegistrado;
import org.example.serverbasket.EE.utils.Constantes;
import org.example.serverbasket.servicios.ServiciosPartido;

import java.util.List;

@UsuarioRegistrado
@Log4j2
@Path(Constantes.PARTIDO)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestPartido {
    private final ServiciosPartido sp;


    @Inject
    public RestPartido(ServiciosPartido sp) {
        this.sp = sp;
    }


    //GetAllPartidos
    @GET
    public Response getAllPartidos() {
        Response response;
        Either<ApiError, List<Partido>> resultado = sp.getAllPartidos();
        if (resultado.isRight()) {
            response = Response.status(Response.Status.ACCEPTED)
                    .entity(resultado.get())
                    .build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(resultado.getLeft())
                    .build();
        }
        return response;
    }

    //GetAllPartidos por jornada
    @GET
    @Path(Constantes.PARTIDOS_JORNADA)
    public Response getAllPartidosPorJornada(@QueryParam(Constantes.ID_JORNADA) int idJornada) {
        Response response;
        Either<ApiError, List<Partido>> resultado = sp.getPartidosPorJornada(idJornada);
        if (resultado.isRight()) {
            response = Response.status(Response.Status.ACCEPTED)
                    .entity(resultado.get())
                    .build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(resultado.getLeft())
                    .build();
        }
        return response;
    }

    //GetAllPartidos por equipo
    @GET
    @Path(Constantes.PARTIDOS_NOMBRE)
    public Response getAllPartidosPorEquipo(@QueryParam(Constantes.NOMBRE_EQUIPO) String nombreEquipo) {
        Response response;
        Either<ApiError, List<Partido>> resultado = sp.getPartidosPorEquipo(nombreEquipo);
        if (resultado.isRight()) {
            response = Response.status(Response.Status.ACCEPTED)
                    .entity(resultado.get())
                    .build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(resultado.getLeft())
                    .build();
        }
        return response;
    }


    //addPartido
    @Admin
    @POST
    public Response addPartido(Partido partido) {
        Response response;
        Either<ApiError, Partido> resultado = sp.addPartido(partido);
        if (resultado.isRight()) {
            response = Response.status(Response.Status.CREATED)
                    .entity(resultado.get())
                    .build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(resultado.getLeft())
                    .build();
        }
        return response;
    }

    //DeleteEquipo
    @Admin
    @DELETE
    public Response deletePartido(@QueryParam(Constantes.ID_JORNADA) int idJornada, @QueryParam(Constantes.NOMBRE_EQUIPO_LOCAL) String nombreEquipoLocal,
                                  @QueryParam(Constantes.NOMBRE_EQUIPO_VISITANTE) String nombreEquipoVisitante) {
        Partido partido = new Partido();
        partido.setIdJornada(idJornada);
        partido.setNombreEquipoVisitante(nombreEquipoVisitante);
        partido.setNombreEquipoLocal(nombreEquipoLocal);
        Response response;
        Either<ApiError, Partido> resultado = sp.deletePartido(partido);
        if (resultado.isRight()) {
            response = Response.status(Response.Status.OK)
                    .entity(resultado.get())
                    .build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(resultado.getLeft())
                    .build();
        }
        return response;
    }

    //UpdateEquipo. Solo puedo poner uno en el body así que el PartidoViejo va en el Body, y pasaré los puntos como dos int, que transformaré aquí.
    @Admin
    @PUT
    public Response updatePartido(Partido partido, @QueryParam(Constantes.PUNTOS_EQUIPO_LOCAL) int puntosEquipoLocal,
                                  @QueryParam(Constantes.PUNTOS_EQUIPO_VISITANTE) int puntosEquipoVisitante) {
        String puntuacion = puntosEquipoLocal + Constantes.GUION + puntosEquipoVisitante;
        Response response;
        Either<ApiError, Partido> resultado = sp.updatePartido(partido, puntuacion);
        if (resultado.isRight()) {
            resultado.get().setPuntosEquipoVisitante(puntosEquipoVisitante);
            resultado.get().setPuntosEquipoLocal(puntosEquipoLocal);
            response = Response.status(Response.Status.OK)
                    .entity(resultado.get())
                    .build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(resultado.getLeft())
                    .build();
        }
        return response;
    }
}
