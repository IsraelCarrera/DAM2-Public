package org.example.serverbasket.ee.rest;

import io.vavr.control.Either;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.log4j.Log4j2;
import org.example.common.modelo.ApiError;
import org.example.common.modelo.User;
import org.example.common.modelo.UsuarioCrear;
import org.example.common.modelo.UsuarioLoggear;
import org.example.serverbasket.ee.utils.Constantes;
import org.example.serverbasket.servicios.ServiciosUsuarios;

import java.util.List;


@Log4j2
@Path(Constantes.USUARIO)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestUsuario {
    private final ServiciosUsuarios su;

    @Inject
    public RestUsuario(ServiciosUsuarios su) {
        this.su = su;
    }

    @PermitAll
    @POST
    public Response addUser(UsuarioCrear usuarioCrear) {
        Response response;
        Either<ApiError, UsuarioLoggear> resultado = su.addUser(usuarioCrear);
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

    @PermitAll
    @GET
    @Path(Constantes.LOG)
    public Response hacerLoggin(@QueryParam(Constantes.NOMBRE_USER) String nombre, @QueryParam(Constantes.CERTIFICADO) String certificadoBase64) {

        Response response;
        UsuarioLoggear usuarioLoggear = UsuarioLoggear.builder().nombre(nombre).certificadoBase64(certificadoBase64).build();
        Either<ApiError, User> resultado = su.logginUser(usuarioLoggear);
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

    @RolesAllowed(Constantes.USER)
    @GET
    public Response getAllUsers() {
        Response response;
        Either<ApiError, List<User>> resultado = su.getAllUsers();
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
