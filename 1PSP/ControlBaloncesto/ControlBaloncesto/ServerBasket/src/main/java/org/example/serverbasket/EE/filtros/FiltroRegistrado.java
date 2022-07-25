package org.example.serverbasket.EE.filtros;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.example.common.error.ApiError;
import org.example.common.modelo.Usuario;
import org.example.serverbasket.EE.utils.Constantes;

import java.time.LocalDate;


@Provider
@UsuarioRegistrado
public class FiltroRegistrado implements ContainerRequestFilter {

    @Context
    private HttpServletRequest httpServletRequest;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute(Constantes.USUARIO_LOGING);
        if (usuario == null) {
            containerRequestContext.abortWith(Response.status(Response.Status.BAD_REQUEST)
                    .entity(ApiError.builder().mensaje(Constantes.LOGGEA_PARA_ACCEDER_A_ESTA_PAGINA).fecha(LocalDate.now()).build())
                    .type(MediaType.APPLICATION_JSON_TYPE).build());
        } else if (!usuario.isEstaActivo()) {
            containerRequestContext.abortWith(Response.status(Response.Status.BAD_REQUEST)
                    .entity(ApiError.builder().mensaje(Constantes.USUARIO_NO_ACTIVO_REVISA_SU_CORREO).fecha(LocalDate.now()).build())
                    .type(MediaType.APPLICATION_JSON_TYPE).build());
        }
    }
}
