package EE;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import utils.Constantes;

import java.io.IOException;

@WebServlet(name = Constantes.NAME_SERVLET_PRIMERO, value = Constantes.VALUE_SERVLET_PRIMERO)
public class ServletPrimero extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        pintar(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        pintar(request,response);
    }

    private void pintar(HttpServletRequest request, HttpServletResponse response) throws  IOException{
        var color = request.getParameter(Constantes.COLOR);

        response.getWriter().println("<html><body style=\"background-color:"+color+ "\";> <p>" + Constantes.HEMOS_CAMBIADO_EL_FONDO_A + color + "</p></body></html>");
    }
}
