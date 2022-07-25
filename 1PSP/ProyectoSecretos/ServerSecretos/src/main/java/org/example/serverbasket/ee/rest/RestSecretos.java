package org.example.serverbasket.ee.rest;

import io.vavr.control.Either;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.log4j.Log4j2;
import org.example.common.modelo.ApiError;
import org.example.common.modelo.Info;
import org.example.common.modelo.SecretosCommon;
import org.example.serverbasket.ee.utils.Constantes;
import org.example.serverbasket.servicios.ServiciosSecretos;
import jakarta.inject.Inject;

import java.util.List;

@RolesAllowed(Constantes.USER)
@Log4j2
@Path(Constantes.SECRETOS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestSecretos {

    private final ServiciosSecretos ss;

    @Inject
    public RestSecretos(ServiciosSecretos ss) {
        this.ss = ss;
    }

    @POST
    public Response addSecreto(SecretosCommon secretosCommon) {
        Response response;
        Either<ApiError, Info> resultado = ss.addSecretosJuntos(secretosCommon);
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

    @GET
    public Response getSecretosUser(@QueryParam(Constantes.NOMBRE_USER) String nombre) {
        Response response;
        Either<ApiError, List<SecretosCommon>> resultado = ss.getAllSecretos(nombre);
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

    @POST
    @Path(Constantes.COMPARTIR)
    public Response compartirSecreto(SecretosCommon secretosCommon) {
        Response response;
        Either<ApiError, Info> resultado = ss.compartirSecreto(secretosCommon);
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
}
