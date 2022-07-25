package org.example.serverbasket.EE.rest;

import io.vavr.control.Either;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.log4j.Log4j2;
import org.example.common.error.ApiError;
import org.example.common.modelo.Jornada;
import org.example.serverbasket.EE.utils.Constantes;
import org.example.serverbasket.servicios.ServiciosJornada;

import java.time.LocalDate;
import java.util.List;


@RolesAllowed(Constantes.USER)
@Log4j2
@Path(Constantes.JORNADA)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestJornada {
    private final ServiciosJornada sj;

    @Inject
    public RestJornada(ServiciosJornada serviciosJornada) {
        this.sj = serviciosJornada;
    }

    //GetAllJornadas
    @GET
    public Response getAllJornadas() {
        Response response;
        Either<ApiError, List<Jornada>> resultado = sj.getAllJornadas();
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

    //addJornada
    @RolesAllowed(Constantes.ADMIN)
    @POST
    public Response addJornada(Jornada jornada) {
        Response response;
        Either<ApiError, Jornada> resultado = sj.addJornada(jornada);
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

    //DeleteJornada
    @RolesAllowed(Constantes.ADMIN)
    @DELETE
    public Response deleteJornada(@QueryParam(Constantes.ID_JORNADA) int idJornada) {
        Response response;
        Either<ApiError, Integer> resultado = sj.deleteJornada(idJornada);
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

    //UpdateJornada No sé por qué, imagino que al ser fecha, no puedo poner el QueryParam, así que lo parseo.
    @RolesAllowed(Constantes.ADMIN)
    @PUT
    public Response updateJornada(Jornada jornada, @QueryParam(Constantes.NUEVA_FECHA) String nuevaFecha) {
        Response response;
        try {
            Either<ApiError, Jornada> resultado = sj.updateJornada(jornada, LocalDate.parse(nuevaFecha));
            if (resultado.isRight()) {
                response = Response.status(Response.Status.OK)
                        .entity(resultado.get())
                        .build();
            } else {
                response = Response.status(Response.Status.NOT_FOUND)
                        .entity(resultado.getLeft())
                        .build();
            }
        } catch (Exception e) {
            log.error(e);
            ApiError apiError = new ApiError(Constantes.FALLO_AL_PONER_LA_FECHA, LocalDate.now());
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(apiError)
                    .build();
        }
        return response;
    }
}
