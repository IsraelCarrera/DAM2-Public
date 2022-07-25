package org.example.serverbasket.EE.servlet;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.common.error.ApiError;
import org.example.serverbasket.EE.utils.Constantes;
import org.example.serverbasket.dao.modelo.UsuarioEntity;
import org.example.serverbasket.servicios.ServiciosUsuario;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

@WebServlet(name = Constantes.ACTIVACION_CUENTA, value = Constantes.ACTIVACION_CUENTA_BARRA)
public class ActivacionCuenta extends HttpServlet {
    private final ServiciosUsuario su;

    @Inject
    public ActivacionCuenta(ServiciosUsuario su) {
        this.su = su;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        activarCuenta(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        activarCuenta(request, response);
    }

    private void activarCuenta(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var codigo = request.getParameter(Constantes.CODIGO_ACTIVACION);
        Either<ApiError, UsuarioEntity> eitherUser = su.getUserByCodigoActivacion(codigo);
        if (eitherUser.isRight()) {
            request.setAttribute(Constantes.NOMBRE_USUARIO, eitherUser.get().getNombreUser());
            if (eitherUser.get() != null && !eitherUser.get().isEstaActivo()) {
                if (Duration.between(LocalDateTime.now(), eitherUser.get().getFechaLimite()).compareTo(Duration.ZERO) >= 0) {
                    if (su.activarUsuario(eitherUser.get().getIdUser()).isRight()) {
                        request.getRequestDispatcher(Constantes.REGISTRO_COMPLETADO_JSP).forward(request, response);
                    } else {
                        request.getRequestDispatcher(Constantes.REGISTRO_FALLO_JSP).forward(request, response);
                    }
                } else {
                    request.setAttribute(Constantes.CODIGO_ACTIVACION, codigo);
                    request.getRequestDispatcher(Constantes.REGISTRO_CADUCADO_JSP).forward(request, response);
                }
            } else {
                request.getRequestDispatcher(Constantes.REGISTRO_HECHO_JSP).forward(request, response);
            }
        } else {
            request.getRequestDispatcher(Constantes.REGISTRO_FALLO_JSP).forward(request, response);
        }
    }
}
