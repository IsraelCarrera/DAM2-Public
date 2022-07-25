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


@WebServlet(name = Constantes.CAMBIAR_PASS_SERVLET, value = Constantes.USER_CAMBIO_PASS)
public class CambiarPassServlet extends HttpServlet {
    private final ServiciosUsuario su;

    @Inject
    public CambiarPassServlet(ServiciosUsuario su) {
        this.su = su;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        cambioPass(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        cambioPass(request, response);
    }

    private void cambioPass(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var codigo = request.getParameter(Constantes.CODIGO_CAMBIO_PASS);
        if (su.comprobarCodigoPass(codigo)) {
            request.setAttribute(Constantes.CODIGO_DE_CAMBIO_PASS, codigo);
            request.getRequestDispatcher(Constantes.CAMBIO_PASS_REALIZAR_JSP).forward(request, response);
        } else {
            request.setAttribute(Constantes.PORQUE_FALLO, Constantes.NO_SE_HA_SOLICITADO_CAMBIO_DE_CONTRASENIA);
            request.getRequestDispatcher(Constantes.CAMBIO_PASS_FALLO_JSP).forward(request, response);
        }
    }
}
