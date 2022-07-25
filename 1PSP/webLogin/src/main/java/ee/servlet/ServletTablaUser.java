package ee.servlet;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servicios.UserServicios;
import utils.Constantes;

import java.io.IOException;

@WebServlet(name = Constantes.SERVLET_TABLA_USER, value = Constantes.TABLA_USER)
public class ServletTablaUser extends HttpServlet {
    UserServicios us;

    @Inject
    public ServletTablaUser(UserServicios us) {
        this.us = us;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        pintarTabla(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        pintarTabla(request, response);
    }

    private void pintarTabla(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(Constantes.LISTA_USER, us.getAll());
        request.getRequestDispatcher(Constantes.PINTAR_TABLA_JSP).forward(request, response);
    }
}
