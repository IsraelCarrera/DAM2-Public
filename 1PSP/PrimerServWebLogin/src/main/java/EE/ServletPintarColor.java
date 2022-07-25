package EE;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import utils.Constantes;

import java.io.IOException;

@WebServlet(name = Constantes.SERVLET_PINTAR_COLOR, value = Constantes.SERVLET_PINTAR_COLOR_BARRA)
public class ServletPintarColor extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Pintar(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Pintar(request,response);
    }

    private void Pintar(HttpServletRequest request, HttpServletResponse response) throws  IOException{
        var color = request.getSession().getAttribute(Constantes.COLOR);
        response.getWriter().println("<html><body style=\"background-color:"+color+ "\";> <p>" + Constantes.HEMOS_CAMBIADO_EL_FONDO_A +"<b>" + color +"</b>" + Constantes.HEMOS_CAMBIADO_EL_FONDO_SESION + "</p></body></html>");

    }
}
