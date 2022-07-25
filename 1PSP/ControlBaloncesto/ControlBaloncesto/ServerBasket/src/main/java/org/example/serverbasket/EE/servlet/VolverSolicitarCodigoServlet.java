package org.example.serverbasket.EE.servlet;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.example.common.error.ApiError;
import org.example.serverbasket.EE.utils.Constantes;
import org.example.serverbasket.servicios.ServiciosUsuario;
import org.example.serverbasket.utils.MandarMail;

import java.io.IOException;
import java.util.List;

@Log4j2
@WebServlet(name = Constantes.VOLVER_SOLICITAR_CODIGO_SERVLET, value = Constantes.REENVIO_CODIGO_ACT)
public class VolverSolicitarCodigoServlet extends HttpServlet {

    private final ServiciosUsuario su;
    private final MandarMail mandarMail;

    @Inject
    public VolverSolicitarCodigoServlet(ServiciosUsuario su, MandarMail mandarMail) {
        this.su = su;
        this.mandarMail = mandarMail;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        volverMandarCuenta(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        volverMandarCuenta(request, response);
    }

    private void volverMandarCuenta(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var codigo = request.getParameter(Constantes.CODIGO_ACT);
        Either<ApiError, List<String>> resultado = su.volverActivarCuenta(codigo);
        if (resultado.isRight()) {
            try {
                String cadena = Constantes.CORREO_REACTIVACION_UNO
                        + resultado.get().get(0) +
                        Constantes.CORREO_REACTIVACION_DOS;
                mandarMail.generateAndSendEmail(resultado.get().get(1), cadena, Constantes.SUBJECT_CORREO_REACTIVACION);
                request.setAttribute(Constantes.SOLUCION_VOLVER_ACTIVAR, Constantes.EL_CORREO_SE_HA_MANDADO_CON_EXITO_COMPRUEBA_TU_BANDEJA_DE_CORREO);
            } catch (MessagingException e) {
                log.error(e.getMessage(), e);
                request.setAttribute(Constantes.SOLUCION_VOLVER_ACTIVAR, Constantes.NO_HA_SIDO_POSIBLE_ENVIAR_EL_CORREO_INTENTELO_MAS_TARDE);
            }
        } else {
            request.setAttribute(Constantes.SOLUCION_VOLVER_ACTIVAR, Constantes.HA_HABIDO_UN_FALLO_INTERNO_INTENTELO_MAS_TARDE);
        }
        request.getRequestDispatcher(Constantes.REGISTRO_VOLVER_ACTIVAR_CUENTA_JSP).forward(request, response);
    }
}
