package ee.servlet.logging;

import dao.modelo.Usuario;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servicios.UserServicios;
import utils.Constantes;

import java.io.IOException;

@WebServlet(name = Constantes.SERVLET_VERIFICAR_LOGGING, value = Constantes.LOG_VERIFICAR)
public class ServletVerificarLogging extends HttpServlet {
    UserServicios us;

    @Inject
    public ServletVerificarLogging(UserServicios us) {
        this.us = us;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        verificarLog(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        verificarLog(request, response);
    }

    private void verificarLog(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var nombre = request.getParameter(Constantes.NOMBRE);
        var pass = request.getParameter(Constantes.PASS);
        Usuario u = new Usuario(nombre, pass);
        if (us.comprobarUsuarioCorrecto(u)) {
            request.getSession().setAttribute(Constantes.USER, u);
            request.getRequestDispatcher(Constantes.LOGGING_INICIO_JSP).forward(request, response);
        } else {
            request.getRequestDispatcher(Constantes.LOGGING_FALSE_JSP).forward(request, response);
        }
    }
}
