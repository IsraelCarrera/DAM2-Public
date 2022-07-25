package ee.servlet.registro;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.Constantes;

import java.io.IOException;

//Tema registro. La idea principal era hacer el registro, para que el usuario pueda ver una tabla de todos
// los usuarios que existen. Pero como leí por el grupo que al final no... Lo reutilicé para registrar para mantener la idea, pero a pelo todo.
@WebServlet(name = Constantes.SERVLET_REGISTRO, value = Constantes.REGISTRO)
public class ServletRegistro extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        mostrar(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        mostrar(request, response);
    }

    private void mostrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(Constantes.REGISTRO_JSP).forward(request, response);
    }
}
