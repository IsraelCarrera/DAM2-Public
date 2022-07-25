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
        mandarAPintarColor(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        mandarAPintarColor(request,response);
    }

    private void mandarAPintarColor(HttpServletRequest request, HttpServletResponse response) throws  IOException{
        var color = request.getParameter(Constantes.COLOR);
        request.getSession().setAttribute(Constantes.COLOR,color);
        response.getWriter().println("<html>");
        response.getWriter().println("<body>");
        response.getWriter().println("<form action=\"ServletPintarColor\">");
        response.getWriter().println("<button>IrAlColorPorSesion</button>");
        response.getWriter().println("</form>");
        response.getWriter().println("</body>");
        response.getWriter().println("</html>");


    }
}
