package ee.servlet;

import dao.modelo.Usuario;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import utils.Constantes;

import java.io.IOException;

@WebFilter(filterName = Constantes.FILTER_LOG, urlPatterns = {Constantes.LOGOUT, Constantes.TABLA_USER})
public class FilterLog implements Filter {

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        Usuario u = (Usuario) ((HttpServletRequest) request).getSession().getAttribute(Constantes.USER);
        if (u != null) {
            chain.doFilter(request, response);
        } else {
            request.getRequestDispatcher(Constantes.ERROR_LOG_JSP).forward(request, response);
        }
    }
}
