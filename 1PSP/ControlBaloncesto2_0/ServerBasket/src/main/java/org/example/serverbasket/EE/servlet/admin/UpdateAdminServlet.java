package org.example.serverbasket.EE.servlet.admin;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.serverbasket.EE.utils.Constantes;
import org.example.serverbasket.servicios.ServiciosUsuario;

import java.io.IOException;


@WebServlet(name = Constantes.UPDATE_ADMIN_SERVLET, value = Constantes.ADMIN_UPDATE_ADMIN)
public class UpdateAdminServlet extends HttpServlet {
    private final ServiciosUsuario su;

    @Inject
    public UpdateAdminServlet(ServiciosUsuario su) {
        this.su = su;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        updatearAAdmin(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        updatearAAdmin(request, response);
    }

    private void updatearAAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var codigo = request.getParameter(Constantes.CODIGO_ADMIN);
        if (su.hacerAdmin(codigo)) {
            request.getRequestDispatcher(Constantes.UPDATE_ADMIN_COMPLETADO_JSP).forward(request, response);
        } else {
            request.getRequestDispatcher(Constantes.UPDATE_ADMIN_FALLO_JSP).forward(request, response);
        }
    }
}
