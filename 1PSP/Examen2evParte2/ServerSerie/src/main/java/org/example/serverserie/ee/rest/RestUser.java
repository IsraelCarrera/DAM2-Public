package org.example.serverserie.ee.rest;


import io.vavr.control.Either;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.log4j.Log4j2;
import org.example.common.modelo.ApiError;
import org.example.common.modelo.Usuario;
import org.example.serverserie.model.UsuarioServer;
import org.example.serverserie.servicios.ServiciosUsuarios;


@PermitAll
@Log4j2
@Path("/usuario")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestUser {
    private final ServiciosUsuarios su;

    @Inject
    public RestUser(ServiciosUsuarios su) {
        this.su = su;
    }

    @POST
    public Response addUser(Usuario usuario) {
        Response response;
        Either<ApiError, UsuarioServer> resultado = su.addUser(usuario);
        if (resultado.isRight()) {
            response = Response.status(Response.Status.CREATED)
                    .entity(Usuario.builder()
                            .id(resultado.get().getId())
                            .nombre(resultado.get().getNombre())
                            .tipo(resultado.get().getTipo())
                            .pass(resultado.get().getPassHash())
                            .build())
                    .build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(resultado.getLeft())
                    .build();
        }
        return response;
    }

    @GET
    public Response getOneUser(@QueryParam("nombre") String nombre, @QueryParam("pass") String pass){
        Response response;
        Either<ApiError,UsuarioServer> resultado = su.getUser(nombre,pass);
        if (resultado.isRight()) {
            response = Response.status(Response.Status.OK)
                    .entity(Usuario.builder()
                            .id(resultado.get().getId())
                            .nombre(resultado.get().getNombre())
                            .tipo(resultado.get().getTipo())
                            .pass("")
                            .build())
                    .build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND)
                    .entity(resultado.getLeft())
                    .build();
        }
        return response;
    }


}
