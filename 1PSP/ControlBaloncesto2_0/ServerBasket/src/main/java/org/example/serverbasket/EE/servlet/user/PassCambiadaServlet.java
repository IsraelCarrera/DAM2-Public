package org.example.serverbasket.EE.servlet.user;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.serverbasket.EE.utils.Constantes;
import org.example.serverbasket.servicios.ServiciosUsuario;

import java.io.IOException;


@WebServlet(name = Constantes.PASS_CAMBIADA_SERVLET, value = Constantes.USER_PASS_CAMBIADA)
public class PassCambiadaServlet extends HttpServlet {
    private final ServiciosUsuario su;

    @Inject
    public PassCambiadaServlet(ServiciosUsuario su) {
        this.su = su;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        realizarCambioPass(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        realizarCambioPass(request, response);
    }

    private void realizarCambioPass(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var codigo = request.getParameter(Constantes.CODIGO_PASS);
        var passAntigua = request.getParameter(Constantes.PASS_ANTIGUA);
        var passNueva = request.getParameter(Constantes.PASS_NUEVA);
        var passNuevaRepit = request.getParameter(Constantes.PASS_NUEVA_REPIT);
        if (passNueva.equals(passNuevaRepit)) {
            if (su.cambiarPass(codigo, passAntigua, passNueva)) {
                request.getRequestDispatcher(Constantes.CAMBIO_PASS_COMPLETADO_JSP).forward(request, response);
            } else {
                request.setAttribute(Constantes.PORQUE_FALLO, Constantes.NO_HA_ESCRITO_CORRECTAMENTE_LA_CONTRASENIA_VIEJA);
                request.getRequestDispatcher(Constantes.CAMBIO_PASS_FALLO_JSP).forward(request, response);
            }
        } else {
            request.setAttribute(Constantes.PORQUE_FALLO, Constantes.LAS_CONTRASENIAS_NUEVAS_NO_SON_IGUALES);
            request.getRequestDispatcher(Constantes.CAMBIO_PASS_FALLO_JSP).forward(request, response);
        }
    }
}
