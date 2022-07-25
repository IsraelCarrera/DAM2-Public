package gui.controllers;

import gui.utils.Constantes;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import lombok.extern.log4j.Log4j2;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

@Log4j2
public class Principal implements Initializable {
    @FXML
    private BorderPane principal;

    public BorderPane getPrincipal() {
        return principal;
    }

    //Pantallas
    private AnchorPane paneInicio;
    private AnchorPane paneMudaciones;
    private AnchorPane paneCasamientos;
    private AnchorPane paneNacimientos;
    private AnchorPane paneDefunciones;
    private AnchorPane paneConsultas;

    //Loaders
    private final FXMLLoader fxmlLoaderInicio;
    private final FXMLLoader fxmlLoaderMudaciones;
    private final FXMLLoader fxmlLoaderCasamientos;
    private final FXMLLoader fxmlLoaderNacimientos;
    private final FXMLLoader fxmlLoaderDefunciones;
    private final FXMLLoader fxmlLoaderConsultas;

    //Constructor


    @Inject
    public Principal(FXMLLoader fxmlLoaderInicio, FXMLLoader fxmlLoaderMudaciones,
                     FXMLLoader fxmlLoaderCasamientos, FXMLLoader fxmlLoaderNacimientos,
                     FXMLLoader fxmlLoaderDefunciones, FXMLLoader fxmlLoaderConsultas) {
        this.fxmlLoaderInicio = fxmlLoaderInicio;
        this.fxmlLoaderMudaciones = fxmlLoaderMudaciones;
        this.fxmlLoaderCasamientos = fxmlLoaderCasamientos;
        this.fxmlLoaderNacimientos = fxmlLoaderNacimientos;
        this.fxmlLoaderDefunciones = fxmlLoaderDefunciones;
        this.fxmlLoaderConsultas = fxmlLoaderConsultas;
    }

    //Las que necesitaré cargar datos justo cuando se vaya a ellas
    private Mudaciones mudaciones;
    private Casamientos casamientos;
    private Nacimientos nacimientos;
    private Defunciones defunciones;
    private Consultas consultas;

    //Carga de pantallas.
    private void cargarInicio() {
        try {
            if (paneInicio == null) {
                paneInicio = fxmlLoaderInicio.load(getClass().getResourceAsStream("/fxml/inicio.fxml"));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void cargarMudaciones() {
        try {
            if (paneMudaciones == null) {
                paneMudaciones = fxmlLoaderMudaciones.load(getClass().getResourceAsStream("/fxml/mudaciones.fxml"));
                mudaciones = fxmlLoaderMudaciones.getController();
                mudaciones.setPrincipal(this);

            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void cargarCasamientos() {
        try {
            if (paneCasamientos == null) {
                paneCasamientos = fxmlLoaderCasamientos.load(getClass().getResourceAsStream("/fxml/casamientos.fxml"));
                casamientos = fxmlLoaderCasamientos.getController();
                casamientos.setPrincipal(this);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void cargarNacimientos() {
        try {
            if (paneNacimientos == null) {
                paneNacimientos = fxmlLoaderNacimientos.load(getClass().getResourceAsStream("/fxml/nacimientos.fxml"));
                nacimientos = fxmlLoaderNacimientos.getController();
                nacimientos.setPrincipal(this);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void cargarDefunciones() {
        try {
            if (paneDefunciones == null) {
                paneDefunciones = fxmlLoaderDefunciones.load(getClass().getResourceAsStream("/fxml/defunciones.fxml"));
                defunciones = fxmlLoaderDefunciones.getController();
                defunciones.setPrincipal(this);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void cargarConsultas() {
        try {
            if (paneConsultas == null) {
                paneConsultas = fxmlLoaderConsultas.load(getClass().getResourceAsStream("/fxml/consultas.fxml"));
                consultas = fxmlLoaderConsultas.getController();
                consultas.setPrincipal(this);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    //Ahora los pases
    private void pasarInicio() {
        if (paneInicio != null) {
            principal.setCenter(paneInicio);
        } else {
            mandarAlert(Alert.AlertType.ERROR, Constantes.LA_PANTALLA_NO_SE_HA_PASADO_CORRECTAMENTE);
        }
    }

    private void pasarMudaciones() {
        if (paneMudaciones != null) {
            principal.setCenter(paneMudaciones);
        } else {
            mandarAlert(Alert.AlertType.ERROR, Constantes.LA_PANTALLA_NO_SE_HA_PASADO_CORRECTAMENTE);
        }
    }

    private void pasarCasamientos() {
        if (paneCasamientos != null) {
            principal.setCenter(paneCasamientos);
        } else {
            mandarAlert(Alert.AlertType.ERROR, Constantes.LA_PANTALLA_NO_SE_HA_PASADO_CORRECTAMENTE);
        }
    }

    private void pasarNacimientos() {
        if (paneNacimientos != null) {
            principal.setCenter(paneNacimientos);
        } else {
            mandarAlert(Alert.AlertType.ERROR, Constantes.LA_PANTALLA_NO_SE_HA_PASADO_CORRECTAMENTE);
        }
    }

    private void pasarDefunciones() {
        if (paneDefunciones != null) {
            principal.setCenter(paneDefunciones);
        } else {
            mandarAlert(Alert.AlertType.ERROR, Constantes.LA_PANTALLA_NO_SE_HA_PASADO_CORRECTAMENTE);
        }
    }

    private void pasarConsultas() {
        if (paneConsultas != null) {
            principal.setCenter(paneConsultas);
        } else {
            mandarAlert(Alert.AlertType.ERROR, Constantes.LA_PANTALLA_NO_SE_HA_PASADO_CORRECTAMENTE);
        }
    }

    //Y los clicks.
    @FXML
    private void irInicio() {
        pasarInicio();
    }

    @FXML
    private void irMudaciones() {
        mudaciones.cargarMudaciones();
        pasarMudaciones();
    }

    @FXML
    private void irCasamientos() {
        casamientos.cargarCasamientos();
        pasarCasamientos();
    }

    @FXML
    private void irNacimientos() {
        nacimientos.cargarNacimientos();
        pasarNacimientos();
    }

    @FXML
    private void irDefunciones() {
        defunciones.cargarDefunciones();
        pasarDefunciones();
    }

    @FXML
    private void irConsultas() {
        pasarConsultas();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cargarCasamientos();
        cargarConsultas();
        cargarDefunciones();
        cargarInicio();
        cargarMudaciones();
        cargarNacimientos();
        pasarInicio();
    }

    public void mandarAlert(Alert.AlertType tipoAlerta, String aviso) {
        Alert alerta = new Alert(tipoAlerta);
        alerta.setContentText(aviso);
        alerta.showAndWait();
    }
}
