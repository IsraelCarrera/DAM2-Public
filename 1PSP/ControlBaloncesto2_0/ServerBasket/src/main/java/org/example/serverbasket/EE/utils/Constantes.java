package org.example.serverbasket.EE.utils;

public class Constantes {
    //Filtros
    public static final String USUARIO_LOGING = "usuarioLoging";
    public static final String NO_PUEDE_ACCEDER_A_ESTE_LUGAR = "No puede acceder a este lugar.";
    public static final String LOGGEA_PARA_ACCEDER_A_ESTA_PAGINA = "Loggea para acceder a esta página.";
    public static final String USUARIO_NO_ACTIVO_REVISA_SU_CORREO = "Usuario no activo. Revisa su correo.";
    //Rest
    public static final String API = "/api";
    public static final String EQUIPO = "/equipo";
    public static final String NOMBRE_EQUIPO = "nombreEquipo";
    public static final String NOMBRE_EQUIPO_NUEVO = "nombreEquipoNuevo";
    public static final String JORNADA = "/jornada";
    public static final String ID_JORNADA = "idJornada";
    public static final String NUEVA_FECHA = "nuevaFecha";
    public static final String FALLO_AL_PONER_LA_FECHA = "Fallo al poner la fecha.";
    public static final String PARTIDO = "/partido";
    public static final String PARTIDOS_JORNADA = "/partidosJornada";
    public static final String PARTIDOS_NOMBRE = "/partidosNombre";
    public static final String NOMBRE_EQUIPO_LOCAL = "nombreEquipoLocal";
    public static final String NOMBRE_EQUIPO_VISITANTE = "nombreEquipoVisitante";
    public static final String PUNTOS_EQUIPO_LOCAL = "puntosEquipoLocal";
    public static final String PUNTOS_EQUIPO_VISITANTE = "puntosEquipoVisitante";
    public static final String GUION = org.example.serverbasket.dao.utils.Constantes.GUION;
    public static final String REGISTRO = "/registro";
    public static final String RECUPERAR_CUENTA = "No se ha podido mandar el mail pero la cuenta se ha creado. Intenta darle a \"recuperar cuenta\"";
    public static final String LOG = "/log";
    public static final String NOMBRE_USER = "nombreUser";
    public static final String PASS = "pass";
    public static final String CORREO_ADD_UNO = "<html><p>Bienvenido a la aplicación de partidos de baloncesto de la liga de Quevedo,";
    public static final String CORREO_ADD_DOS = "</p> <p> Para tener la cuenta activa, haz click <a href= \"http://localhost:8080/ServerBasket-1.0-SNAPSHOT/ActivacionCuenta?codigoActivacion=";
    public static final String CORREO_ADD_TRES = "\" >aquí</a> </p></html>";
    public static final String SUBJECT_CORREO_ADD = "Email de activación.";
    public static final String USUARIO = "/usuario";
    public static final String CAMBIAR_PASS = "/cambiarPass";
    public static final String SE_HA_PROCEDIDO_AL_ENVIO_DEL_CORREO_DE_CAMBIO_DE_CONTRASENIA = "Se ha procedido al envio del correo de cambio de contraseña.";
    public static final String NO_HA_SIDO_POSIBLE_MANDAR_EL_CORREO_INTENTELO_MAS_TARDE = "No ha sido posible mandar el correo. Inténtelo más tarde.";
    public static final String ID_USER = "idUser";
    public static final String UPDATE_ADMIN = "/updateAdmin";
    public static final String ID = "id";
    public static final String CORREO = "correo";
    public static final String SE_HA_PROCEDIDO_A_HACER_EL_ENVIO_DEL_CORREO_ELECTRONICO_PARA_CONFIRMAR_QUE_EL_USUARIO_AHORA_ES_ADMIN = "Se ha procedido a hacer el envío del correo electrónico para confirmar que el usuario ahora es admin.";
    public static final String CORREO_SER_ADMIN_UNO = "<html><p>Mensaje para el administrador. Ha decidido hacer admin a un usuario. </p> <p> Para que el usuario sea admin, haz click <a href= \"http://localhost:8080/ServerBasket-1.0-SNAPSHOT/admin/updateAdmin?codigoAdmin=";
    public static final String CORREO_SER_ADMIN_DOS = "\" >aquí</a> </p> </html>";
    public static final String SUBJECT_CORREO_SER_ADMIN = "Cambiar usuario a Administrador.";
    public static final String CORREO_CAMBIAR_PASS_UNO = "<html><p>Solicitud de cambio de contraseña.</p><p> Ha solicitado cambiar la contraseña. Para continuar el proceso de cambio de contraseña, haz click  <a href= \"http://localhost:8080/ServerBasket-1.0-SNAPSHOT/user/cambioPass?codigoCambioPass=";
    public static final String CORREO_CAMBIAR_PASS_DOS = "\" >aquí</a> </p></html>";
    public static final String SUBJECT_CAMBIAR_PASS = "Cambiar la contraseña.";

    //Servlets
    public static final String UPDATE_ADMIN_SERVLET = "UpdateAdminServlet";
    public static final String ADMIN_UPDATE_ADMIN = "/admin/updateAdmin";
    public static final String CODIGO_ADMIN = "codigoAdmin";
    public static final String UPDATE_ADMIN_COMPLETADO_JSP = "../updateAdminCompletado.jsp";

    public static final String UPDATE_ADMIN_FALLO_JSP = "../updateAdminFallo.jsp";
    public static final String CAMBIAR_PASS_SERVLET = "CambiarPassServlet";
    public static final String USER_CAMBIO_PASS = "/user/cambioPass";
    public static final String CODIGO_CAMBIO_PASS = "codigoCambioPass";
    public static final String CODIGO_DE_CAMBIO_PASS = "codigoDeCambioPass";
    public static final String CAMBIO_PASS_REALIZAR_JSP = "../cambioPassRealizar.jsp";
    public static final String NO_SE_HA_SOLICITADO_CAMBIO_DE_CONTRASENIA = "No se ha solicitado cambio de contraseña.";
    public static final String PORQUE_FALLO = "porqueFallo";
    public static final String CAMBIO_PASS_FALLO_JSP = "../cambioPassFallo.jsp";
    public static final String PASS_CAMBIADA_SERVLET = "PassCambiadaServlet";
    public static final String USER_PASS_CAMBIADA = "/user/PassCambiada";
    public static final String CODIGO_PASS = "codigoPass";
    public static final String PASS_ANTIGUA = "PassAntigua";
    public static final String PASS_NUEVA = "passNueva";
    public static final String PASS_NUEVA_REPIT = "passNuevaRepit";
    public static final String CAMBIO_PASS_COMPLETADO_JSP = "../cambioPassCompletado.jsp";
    public static final String NO_HA_ESCRITO_CORRECTAMENTE_LA_CONTRASENIA_VIEJA = "No ha escrito correctamente la contraseña vieja.";
    public static final String LAS_CONTRASENIAS_NUEVAS_NO_SON_IGUALES = "Las contraseñas nuevas no son iguales.";
    public static final String ACTIVACION_CUENTA = "ActivacionCuenta";
    public static final String ACTIVACION_CUENTA_BARRA = "/ActivacionCuenta";
    public static final String CODIGO_ACTIVACION = "codigoActivacion";
    public static final String REGISTRO_COMPLETADO_JSP = "registroCompletado.jsp";
    public static final String NOMBRE_USUARIO = "nombreUsuario";
    public static final String REGISTRO_FALLO_JSP = "registroFallo.jsp";
    public static final String REGISTRO_CADUCADO_JSP = "registroCaducado.jsp";
    public static final String REGISTRO_HECHO_JSP = "registroHecho.jsp";
    public static final String VOLVER_SOLICITAR_CODIGO_SERVLET = "VolverSolicitarCodigoServlet";
    public static final String REENVIO_CODIGO_ACT = "/reenvioCodigoAct";
    public static final String CODIGO_ACT = "codigoAct";
    public static final String SOLUCION_VOLVER_ACTIVAR = "solucionVolverActivar";
    public static final String EL_CORREO_SE_HA_MANDADO_CON_EXITO_COMPRUEBA_TU_BANDEJA_DE_CORREO = "El correo se ha mandado con éxito. Comprueba tu bandeja de correo.";
    public static final String NO_HA_SIDO_POSIBLE_ENVIAR_EL_CORREO_INTENTELO_MAS_TARDE = "No ha sido posible enviar el correo. Inténtelo más tarde.";
    public static final String HA_HABIDO_UN_FALLO_INTERNO_INTENTELO_MAS_TARDE = "Ha habido un fallo interno. Inténtelo más tarde.";
    public static final String REGISTRO_VOLVER_ACTIVAR_CUENTA_JSP = "registroVolverActivarCuenta.jsp";
    public static final String SUBJECT_CORREO_REACTIVACION = "Email de activación.";
    public static final String CORREO_REACTIVACION_UNO = "<html><p>Reenviando un nuevo código para activación de cuenta.</p> <p> Para tener la cuenta activa, haz click <a href= \"http://localhost:8080/ServerBasket-1.0-SNAPSHOT/ActivacionCuenta?codigoActivacion=";
    public static final String CORREO_REACTIVACION_DOS = "\" >aquí</a> </p></html>";

    //Security
    public static final String ADMIN = "admin";
    public static final String USER = "user";
    public static final String BASIC = "Basic";
    public static final String BEARER = "Bearer";
    public static final String DOS_PUNTOS = ":";
    public static final String GRUPO = "grupo";
    public static final String VUELVE_A_LOGGEAR = "Vuelve a loggear";
    public static final String JWT = "JWT";
}
