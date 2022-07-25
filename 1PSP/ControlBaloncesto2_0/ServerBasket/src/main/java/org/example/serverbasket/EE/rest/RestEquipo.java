package org.example.serverbasket.EE.rest;

import io.vavr.control.Either;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.log4j.Log4j2;
import org.example.common.error.ApiError;
import org.example.common.modelo.Equipo;
import org.example.serverbasket.EE.utils.Constantes;
import org.example.serverbasket.servicios.ServiciosEquipo;

import java.util.List;


@RolesAllowed(Constantes.USER)
@Log4j2
@Path(Constantes.EQUIPO)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestEquipo {
    private final ServiciosEquipo se;

    @Inject
    public RestEquipo(ServiciosEquipo se) {
        this.se = se;
    }

    //GetAllEquipos
    @GET
    public Response getAllEquipos() {
        Response response;
        Either<ApiError, List<Equipo>> resultado = se.getAllEquipos();
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

    //addEquipo
    @RolesAllowed(Constantes.ADMIN)
    @POST
    public Response addEquipo(Equipo equipo) {
        Response response;
        Either<ApiError, Equipo> resultado = se.addEquipo(equipo);
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
    @RolesAllowed(Constantes.ADMIN)
    @DELETE
    public Response deleteEquipo(@QueryParam(Constantes.NOMBRE_EQUIPO) String nombreEquipo) {
        Equipo equipo = new Equipo(nombreEquipo);
        Response response;
        Either<ApiError, Equipo> resultado = se.deleteEquipo(equipo);
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

    //UpdateEquipo
    @RolesAllowed(Constantes.ADMIN)
    @PUT
    public Response updateEquipo(Equipo equipo, @QueryParam(Constantes.NOMBRE_EQUIPO_NUEVO) String nombreEquipoNuevo) {
        Response response;
        Either<ApiError, Equipo> resultado = se.updateEquipo(equipo, nombreEquipoNuevo);
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

}
