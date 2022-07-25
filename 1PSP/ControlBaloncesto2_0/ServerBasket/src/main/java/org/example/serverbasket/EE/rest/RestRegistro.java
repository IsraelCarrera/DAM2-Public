package org.example.serverbasket.EE.rest;

import io.vavr.control.Either;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.mail.MessagingException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.log4j.Log4j2;
import org.example.common.error.ApiError;
import org.example.common.modelo.Usuario;
import org.example.common.modelo.UsuarioLogging;
import org.example.serverbasket.EE.utils.Constantes;
import org.example.serverbasket.servicios.ServiciosUsuario;
import org.example.serverbasket.utils.MandarMail;

import java.time.LocalDate;


@Log4j2
@Path(Constantes.REGISTRO)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestRegistro {
    private final ServiciosUsuario su;
    private final MandarMail mandarMail;

    @Inject
    public RestRegistro(ServiciosUsuario su, MandarMail mandarMail) {
        this.su = su;
        this.mandarMail = mandarMail;
    }

    @PermitAll
    @POST
    public Response addUser(Usuario u) {
        Response response;
        Either<ApiError, Usuario> resultado = su.addUser(u);
        if (resultado.isRight()) {
            try {
                String cadena = Constantes.CORREO_ADD_UNO
                        + resultado.get().getNombreUser()
                        + Constantes.CORREO_ADD_DOS
                        + resultado.get().getCodigoActivacion()
                        + Constantes.CORREO_ADD_TRES;
                mandarMail.generateAndSendEmail(resultado.get().getCorreoElectronico(), cadena, Constantes.SUBJECT_CORREO_ADD);
                response = Response.status(Response.Status.CREATED)
                        .entity(resultado.get())
                        .build();
            } catch (MessagingException e) {
                log.error(e.getMessage(), e);
                response = Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiError.builder()
                                .mensaje(Constantes.RECUPERAR_CUENTA)
                                .fecha(LocalDate.now())
                                .build())
                        .build();
            }
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
    public Response hacerLoging(@QueryParam(Constantes.NOMBRE_USER) String nombreUser, @QueryParam(Constantes.PASS) String pass) {
        UsuarioLogging u = new UsuarioLogging(nombreUser, pass);
        Response response;
        Either<ApiError, Usuario> resultado = su.hacerLogging(u);
        if (resultado.isRight()) {
            response = Response.status(Response.Status.OK)
                    .entity(resultado.get())
                    .build();
        } else {
            response = Response.status(Response.Status.UNAUTHORIZED)
                    .entity(resultado.getLeft())
                    .build();
        }
        return response;
    }
}
