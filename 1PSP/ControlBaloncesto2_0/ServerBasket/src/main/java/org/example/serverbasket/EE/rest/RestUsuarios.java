package org.example.serverbasket.EE.rest;

import io.vavr.control.Either;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.mail.MessagingException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.log4j.Log4j2;
import org.example.common.error.ApiError;
import org.example.common.modelo.Avisos;
import org.example.common.modelo.Usuario;
import org.example.serverbasket.EE.utils.Constantes;
import org.example.serverbasket.servicios.ServiciosUsuario;
import org.example.serverbasket.utils.MandarMail;

import java.time.LocalDate;
import java.util.List;


@RolesAllowed(Constantes.USER)
@Log4j2
@Path(Constantes.USUARIO)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestUsuarios {
    private final ServiciosUsuario su;
    private final MandarMail mandarMail;

    @Inject
    public RestUsuarios(ServiciosUsuario su, MandarMail mandarMail) {
        this.su = su;
        this.mandarMail = mandarMail;
    }

    @PUT
    @Path(Constantes.CAMBIAR_PASS)
    public Response cambiarContraseña(@QueryParam(Constantes.NOMBRE_USER) String nombreUser) {
        Response response;
        Either<ApiError, List<String>> resultado = su.cambiarCodigoPass(nombreUser);
        //Recuerdo: 0 codigo, 1 correo.
        if (resultado.isRight()) {
            try {
                String cadena = Constantes.CORREO_CAMBIAR_PASS_UNO
                        + resultado.get().get(0) +
                        Constantes.CORREO_CAMBIAR_PASS_DOS;
                mandarMail.generateAndSendEmail(resultado.get().get(1), cadena, Constantes.SUBJECT_CAMBIAR_PASS);
                response = Response.status(Response.Status.OK)
                        .entity(Avisos.builder()
                                .aviso(Constantes.SE_HA_PROCEDIDO_AL_ENVIO_DEL_CORREO_DE_CAMBIO_DE_CONTRASENIA)
                                .build())
                        .build();
            } catch (MessagingException e) {
                log.error(e.getMessage(), e);
                response = Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiError.builder()
                                .mensaje(Constantes.NO_HA_SIDO_POSIBLE_MANDAR_EL_CORREO_INTENTELO_MAS_TARDE)
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


    @DELETE
    public Response deleteUser(@QueryParam(Constantes.ID_USER) int idUser) {
        Response response;
        Either<ApiError, Integer> resultado = su.deleteUser(idUser);
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


    @RolesAllowed(Constantes.ADMIN)
    @GET
    public Response getAllUser() {
        Response response;
        Either<ApiError, List<Usuario>> resultado = su.getAllUsers();
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

    @RolesAllowed(Constantes.ADMIN)
    @PUT
    @Path(Constantes.UPDATE_ADMIN)
    public Response hacerAdmin(@QueryParam(Constantes.ID) int id, @QueryParam(Constantes.CORREO) String correo) {
        Response response;
        Either<ApiError, String> resultado = su.MandarCodigoParaHacerAdmin(id);
        if (resultado.isRight()) {
            try {
                String cadena = Constantes.CORREO_SER_ADMIN_UNO
                        + resultado.get() +
                        Constantes.CORREO_SER_ADMIN_DOS;
                mandarMail.generateAndSendEmail(correo, cadena, Constantes.SUBJECT_CORREO_SER_ADMIN);
                response = Response.status(Response.Status.OK)
                        .entity(Avisos.builder()
                                .aviso(Constantes.SE_HA_PROCEDIDO_A_HACER_EL_ENVIO_DEL_CORREO_ELECTRONICO_PARA_CONFIRMAR_QUE_EL_USUARIO_AHORA_ES_ADMIN)
                                .build())
                        .build();
            } catch (MessagingException e) {
                log.error(e.getMessage(), e);
                response = Response.status(Response.Status.NOT_FOUND)
                        .entity(ApiError.builder()
                                .mensaje(Constantes.NO_HA_SIDO_POSIBLE_MANDAR_EL_CORREO_INTENTELO_MAS_TARDE)
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
}
