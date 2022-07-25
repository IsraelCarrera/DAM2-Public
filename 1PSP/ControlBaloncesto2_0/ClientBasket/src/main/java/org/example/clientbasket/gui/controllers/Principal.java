package org.example.clientbasket.gui.controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import lombok.extern.log4j.Log4j2;
import org.example.clientbasket.dao.utils.UserCacheado;
import org.example.clientbasket.gui.controllers.basket.*;
import org.example.clientbasket.gui.controllers.cuenta.*;
import org.example.clientbasket.gui.utils.Constantes;
import org.example.common.modelo.Usuario;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

//Usuario Admin estandar: user: root // pass: root
//Los demás usuarios que he creado los he borrado, salvo dos, uno admin y uno normal, llamado primer y segundo, ambos con pass "pass".
@Log4j2
public class Principal implements Initializable {
    //Loaders
    private final FXMLLoader fxmlLoaderAddPartidos;
    private final FXMLLoader fxmlLoaderCambiarEquipos;
    private final FXMLLoader fxmlLoaderCambiarJornadas;
    private final FXMLLoader fxmlLoaderCambiarPartidos;
    private final FXMLLoader fxmlLoaderCuenta;
    private final FXMLLoader fxmlLoaderInicio;
    private final FXMLLoader fxmlLoaderLogging;
    private final FXMLLoader fxmlLoaderRegistro;
    private final FXMLLoader fxmlLoaderUpdateAdmin;
    private final FXMLLoader fxmlLoaderVerPartidos;
    //Cosas a usar del principal
    @FXML
    private BorderPane principal;
    @FXML
    private Menu menuCargaDatos;
    @FXML
    private MenuItem miUpdateAdmin;
    @FXML
    private MenuBar mbSuperior;
    //Para el control de qué puede ver un usuario o no, vamos a tener un objeto Usuario siempre activo.
    private Usuario usuarioActual;
    private final UserCacheado userCacheado;
    //Pantallas
    private AnchorPane paneAddPartidos;
    private AnchorPane paneCambiarEquipos;
    private AnchorPane paneCambiarJornadas;
    private AnchorPane paneCambiarPartidos;
    private AnchorPane paneCuenta;
    private AnchorPane paneInicio;
    private AnchorPane paneLogging;
    private AnchorPane paneRegistro;
    private AnchorPane paneUpdateAdmin;
    private AnchorPane paneVerPartidos;
    //Las que cargan datos cuando voy a ellas y/o tengo que pasar el cursor.
    private AddPartidos addPartidos;
    private UpdateAdmin updateAdmin;

    //Constructor
    private CambiarEquipo cambiarEquipo;
    private CambiarPartidos cambiarPartidos;
    private CambiarJornadas cambiarJornadas;
    private VerPartidos verPartidos;
    private Registro registro;
    private Logging logging;
    private Cuenta cuenta;
    private Inicio inicio;

    @Inject
    public Principal(FXMLLoader fxmlLoaderAddPartidos, FXMLLoader fxmlLoaderCambiarEquipos, FXMLLoader fxmlLoaderCambiarJornadas, FXMLLoader fxmlLoaderCambiarPartidos,
                     FXMLLoader fxmlLoaderCuenta, FXMLLoader fxmlLoaderInicio, FXMLLoader fxmlLoaderLogging, FXMLLoader fxmlLoaderRegistro,
                     FXMLLoader fxmlLoaderUpdateAdmin, FXMLLoader fxmlLoaderVerPartidos, UserCacheado userCacheado) {
        this.fxmlLoaderAddPartidos = fxmlLoaderAddPartidos;
        this.fxmlLoaderCambiarEquipos = fxmlLoaderCambiarEquipos;
        this.fxmlLoaderCambiarJornadas = fxmlLoaderCambiarJornadas;
        this.fxmlLoaderCambiarPartidos = fxmlLoaderCambiarPartidos;
        this.fxmlLoaderCuenta = fxmlLoaderCuenta;
        this.fxmlLoaderInicio = fxmlLoaderInicio;
        this.fxmlLoaderLogging = fxmlLoaderLogging;
        this.fxmlLoaderRegistro = fxmlLoaderRegistro;
        this.fxmlLoaderUpdateAdmin = fxmlLoaderUpdateAdmin;
        this.fxmlLoaderVerPartidos = fxmlLoaderVerPartidos;
        this.userCacheado = userCacheado;
    }

    public BorderPane getPrincipal() {
        return principal;
    }

    public void setUserActual(Usuario user) {
        usuarioActual = user;
    }

    //Carga de pantallas
    private void cargarAddPartidos() {
        try {
            if (paneAddPartidos == null) {
                paneAddPartidos = fxmlLoaderAddPartidos.load(getClass().getResourceAsStream(Constantes.FXML_ADD_PARTIDOS_FXML));
                addPartidos = fxmlLoaderAddPartidos.getController();
                addPartidos.setPrincipal(this);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void cargarUpdateAdmin() {
        try {
            if (paneUpdateAdmin == null) {
                paneUpdateAdmin = fxmlLoaderUpdateAdmin.load(getClass().getResourceAsStream(Constantes.FXML_UPDATE_ADMIN_FXML));
                updateAdmin = fxmlLoaderUpdateAdmin.getController();
                updateAdmin.setPrincipal(this);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void cargarCambiarEquipo() {
        try {
            if (paneCambiarEquipos == null) {
                paneCambiarEquipos = fxmlLoaderCambiarEquipos.load(getClass().getResourceAsStream(Constantes.FXML_CAMBIAR_EQUIPO_FXML));
                cambiarEquipo = fxmlLoaderCambiarEquipos.getController();
                cambiarEquipo.setPrincipal(this);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void cargarCambiarPartidos() {
        try {
            if (paneCambiarPartidos == null) {
                paneCambiarPartidos = fxmlLoaderCambiarPartidos.load(getClass().getResourceAsStream(Constantes.FXML_CAMBIAR_PARTIDOS_FXML));
                cambiarPartidos = fxmlLoaderCambiarPartidos.getController();
                cambiarPartidos.setPrincipal(this);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void cargarCambiarJornadas() {
        try {
            if (paneCambiarJornadas == null) {
                paneCambiarJornadas = fxmlLoaderCambiarJornadas.load(getClass().getResourceAsStream(Constantes.FXML_CAMBIAR_JORNADAS_FXML));
                cambiarJornadas = fxmlLoaderCambiarJornadas.getController();
                cambiarJornadas.setPrincipal(this);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void cargarVerPartidos() {
        try {
            if (paneVerPartidos == null) {
                paneVerPartidos = fxmlLoaderVerPartidos.load(getClass().getResourceAsStream(Constantes.FXML_VER_PARTIDOS_FXML));
                verPartidos = fxmlLoaderVerPartidos.getController();
                verPartidos.setPrincipal(this);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void cargarRegistro() {
        try {
            if (paneRegistro == null) {
                paneRegistro = fxmlLoaderRegistro.load(getClass().getResourceAsStream(Constantes.FXML_REGISTRO_FXML));
                registro = fxmlLoaderRegistro.getController();
                registro.setPrincipal(this);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void cargarLogging() {
        try {
            if (paneLogging == null) {
                paneLogging = fxmlLoaderLogging.load(getClass().getResourceAsStream(Constantes.FXML_LOGGING_FXML));
                logging = fxmlLoaderLogging.getController();
                logging.setPrincipal(this);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void cargarCuenta() {
        try {
            if (paneCuenta == null) {
                paneCuenta = fxmlLoaderCuenta.load(getClass().getResourceAsStream(Constantes.FXML_CUENTA_FXML));
                cuenta = fxmlLoaderCuenta.getController();
                cuenta.setPrincipal(this);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void cargarInicio() {
        try {
            if (paneInicio == null) {
                paneInicio = fxmlLoaderInicio.load(getClass().getResourceAsStream(Constantes.FXML_INICIO_FXML));
                inicio = fxmlLoaderInicio.getController();
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    //Los pases

    private void pasarAddPartido() {
        if (paneAddPartidos != null) {
            principal.setCenter(paneAddPartidos);
        } else {
            mandarAlert(Alert.AlertType.ERROR, Constantes.LA_PANTALLA_NO_SE_HA_PASADO_CORRECTAMENTE);
        }
    }

    private void pasarUpdateAdmin() {
        if (paneUpdateAdmin != null) {
            principal.setCenter(paneUpdateAdmin);
        } else {
            mandarAlert(Alert.AlertType.ERROR, Constantes.LA_PANTALLA_NO_SE_HA_PASADO_CORRECTAMENTE);
        }
    }

    private void pasarCambiarEquipos() {
        if (paneCambiarEquipos != null) {
            principal.setCenter(paneCambiarEquipos);
        } else {
            mandarAlert(Alert.AlertType.ERROR, Constantes.LA_PANTALLA_NO_SE_HA_PASADO_CORRECTAMENTE);
        }
    }

    private void pasarCambiarPartidos() {
        if (paneCambiarPartidos != null) {
            principal.setCenter(paneCambiarPartidos);
        } else {
            mandarAlert(Alert.AlertType.ERROR, Constantes.LA_PANTALLA_NO_SE_HA_PASADO_CORRECTAMENTE);
        }
    }

    private void pasarCambiarJornadas() {
        if (paneCambiarJornadas != null) {
            principal.setCenter(paneCambiarJornadas);
        } else {
            mandarAlert(Alert.AlertType.ERROR, Constantes.LA_PANTALLA_NO_SE_HA_PASADO_CORRECTAMENTE);
        }
    }

    private void pasarVerPartidos() {
        if (paneVerPartidos != null) {
            principal.setCenter(paneVerPartidos);
        } else {
            mandarAlert(Alert.AlertType.ERROR, Constantes.LA_PANTALLA_NO_SE_HA_PASADO_CORRECTAMENTE);
        }
    }

    private void pasarLogging() {
        if (paneLogging != null) {
            principal.setCenter(paneLogging);
        } else {
            mandarAlert(Alert.AlertType.ERROR, Constantes.LA_PANTALLA_NO_SE_HA_PASADO_CORRECTAMENTE);
        }
    }

    private void pasarCuenta() {
        if (paneCuenta != null) {
            principal.setCenter(paneCuenta);
        } else {
            mandarAlert(Alert.AlertType.ERROR, Constantes.LA_PANTALLA_NO_SE_HA_PASADO_CORRECTAMENTE);
        }
    }

    private void pasarInicio() {
        if (paneInicio != null) {
            principal.setCenter(paneInicio);
        } else {
            mandarAlert(Alert.AlertType.ERROR, Constantes.LA_PANTALLA_NO_SE_HA_PASADO_CORRECTAMENTE);
        }
    }

    //Los clicks.
    @FXML
    private void irInicio() {
        inicio.setUsuarioActual(usuarioActual);
        inicio.ponerFraseInicio();
        pasarInicio();
    }

    @FXML
    private void irModificarCuenta() {
        cuenta.setUsuario(usuarioActual);
        pasarCuenta();
    }

    @FXML
    private void irUpdateAdmin() {
        updateAdmin.cargarUpdateAdmin();
        updateAdmin.setUsuario(usuarioActual);
        pasarUpdateAdmin();
    }

    @FXML
    private void irVerPartidos() {
        verPartidos.preCargaVerPartidos();
        pasarVerPartidos();
    }

    @FXML
    private void irCambiarPartidos() {
        cambiarPartidos.cargarCambiarPartidos();
        pasarCambiarPartidos();
    }

    @FXML
    private void irCambiarJornada() {
        cambiarJornadas.cargarCambiarJornadas();
        pasarCambiarJornadas();
    }

    @FXML
    private void irCambiarEquipo() {
        cambiarEquipo.cargarCambiarEquipo();
        pasarCambiarEquipos();
    }

    @FXML
    private void irAddPartido() {
        addPartidos.cargarAddPartidos();
        pasarAddPartido();
    }

    @FXML
    private void irALogging() {
        usuarioActual = null;
        userCacheado.setUsuario(null);
        userCacheado.setPass(null);
        userCacheado.setToken(null);
        noEresNadie();
        pasarLogging();
    }

    //Para mandar alerts sin botón de confirmación.
    public void mandarAlert(Alert.AlertType tipoAlerta, String aviso) {
        Alert alerta = new Alert(tipoAlerta);
        alerta.setContentText(aviso);
        alerta.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cargarAddPartidos();
        cargarCambiarEquipo();
        cargarCambiarJornadas();
        cargarInicio();
        cargarCuenta();
        cargarCambiarPartidos();
        cargarLogging();
        cargarUpdateAdmin();
        cargarVerPartidos();
        cargarRegistro();
        pasarLogging();
        usuarioActual = null;
        logging.panesNecesariosLogging(paneRegistro, paneInicio, inicio);
        registro.panesNecesariosRegistro(paneLogging);
        cuenta.necesarioCuenta(paneLogging);
        noEresNadie();
    }

    private void noEresNadie() {
        mbSuperior.setVisible(false);

    }

    //Son públicas porque se accede desde logging
    public void eresNormal() {
        mbSuperior.setVisible(true);
        menuCargaDatos.setVisible(false);
        miUpdateAdmin.setVisible(false);
    }

    public void eresAdmin() {
        mbSuperior.setVisible(true);
        menuCargaDatos.setVisible(true);
        miUpdateAdmin.setVisible(true);
    }
}
