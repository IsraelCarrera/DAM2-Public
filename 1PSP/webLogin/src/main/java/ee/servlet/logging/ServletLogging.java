package ee.servlet.logging;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.Constantes;

import java.io.IOException;

@WebServlet(name = Constantes.SERVLET_LOGGING, value = Constantes.LOGGING)
public class ServletLogging extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        hacerLogging(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        hacerLogging(request, response);
    }

    private void hacerLogging(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(Constantes.LOGGING_JSP).forward(request, response);
    }
}
