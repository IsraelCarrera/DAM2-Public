package ee.servlet.registro;

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

@WebServlet(name = Constantes.SERVLET_REGISTRO_OK, value = Constantes.VERI_REGISTRO)
public class ServletVerificarRegistro extends HttpServlet {
    private final UserServicios us;

    @Inject
    public ServletVerificarRegistro(UserServicios us) {
        this.us = us;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        recolectarInfo(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        recolectarInfo(request, response);
    }

    private void recolectarInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var nombre = request.getParameter(Constantes.NOMBRE_USUARIO);
        var pass = request.getParameter(Constantes.PASS_USUARIO);
        if (nombre != null && pass != null && !nombre.isBlank() && !pass.isBlank()) {
            Usuario u = new Usuario(nombre, pass);
            Usuario usuarioRegistradoYa = us.addUser(u);
            request.setAttribute(Constantes.USER_REGISTRADO, usuarioRegistradoYa);
            request.getRequestDispatcher(Constantes.REGISTRO_OK_JSP).forward(request, response);
        }
        request.getRequestDispatcher(Constantes.REGISTRO_ERROR_JSP).forward(request, response);
    }
}
