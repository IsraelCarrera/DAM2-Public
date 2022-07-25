package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import utils.Constantes;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PrincipalPantalla implements Initializable {
    @FXML
    private BorderPane principal;

    //Pantallas que utilizaremos.
    //Inicio
    private AnchorPane paneInicioPantalla;
    private AnchorPane panePelisPorIdPantalla;
    private AnchorPane panePelisPopularesPantalla;
    private AnchorPane panePelisColeccionesPantalla;
    private AnchorPane paneEncontrarPersonasPantalla;
    private AnchorPane paneSeriesEncontrarIdPantalla;
    private AnchorPane paneSeriesDetalladaPantalla;

    //Si quisieramos hacer algún método nada más carga, llamamos a la clase pantalla también

    //Ahora cargamos las pantallas

    private void cargarInicio() {
        FXMLLoader fxmlLoaderInicioPantalla = new FXMLLoader();
        try {
            if (paneInicioPantalla == null) {
                paneInicioPantalla = fxmlLoaderInicioPantalla.load(getClass().getResourceAsStream("/fxml/inicioPantalla.fxml"));
            }
        } catch (Exception ex) {
            Logger.getLogger(Constantes.ERROR_CARGAR).log(Level.SEVERE, Constantes.NO_CARGAR, ex);
        }
    }

    private void cargarPelisPorId() {
        FXMLLoader fxmlLoaderPelisPorId = new FXMLLoader();
        try {
            if (panePelisPorIdPantalla == null) {
                panePelisPorIdPantalla = fxmlLoaderPelisPorId.load(getClass().getResourceAsStream("/fxml/buscarPeliculaPantalla.fxml"));
            }
        } catch (Exception ex) {
            Logger.getLogger(Constantes.ERROR_CARGAR).log(Level.SEVERE, Constantes.NO_CARGAR, ex);
        }
    }

    private void cargarPelisPopulares() {
        FXMLLoader fxmlLoaderPelisPopulares = new FXMLLoader();
        try {
            if (panePelisPopularesPantalla == null) {
                panePelisPopularesPantalla = fxmlLoaderPelisPopulares.load(getClass().getResourceAsStream("/fxml/pelisPopularesPantalla.fxml"));
            }
        } catch (Exception ex) {
            Logger.getLogger(Constantes.ERROR_CARGAR).log(Level.SEVERE, Constantes.NO_CARGAR, ex);
        }
    }

    private void cargarPelisColecciones() {
        FXMLLoader fxmlLoaderPelisColecciones = new FXMLLoader();
        try {
            if (panePelisColeccionesPantalla == null) {
                panePelisColeccionesPantalla = fxmlLoaderPelisColecciones.load(getClass().getResourceAsStream("/fxml/pelisColeccionesPantalla.fxml"));
            }
        } catch (Exception ex) {
            Logger.getLogger(Constantes.ERROR_CARGAR).log(Level.SEVERE, Constantes.NO_CARGAR, ex);
        }
    }

    private void cargarEncontrarPersona() {
        FXMLLoader fxmlLoaderEncontrarPersona = new FXMLLoader();
        try {
            if (paneEncontrarPersonasPantalla == null) {
                paneEncontrarPersonasPantalla = fxmlLoaderEncontrarPersona.load(getClass().getResourceAsStream("/fxml/PersonasEncontrarPantalla.fxml"));
            }
        } catch (Exception ex) {
            Logger.getLogger(Constantes.ERROR_CARGAR).log(Level.SEVERE, Constantes.NO_CARGAR, ex);
        }
    }

    private void cargarSeriesPorId() {
        FXMLLoader fxmlLoaderSeriesPorId = new FXMLLoader();
        try {
            if (paneSeriesEncontrarIdPantalla == null) {
                paneSeriesEncontrarIdPantalla = fxmlLoaderSeriesPorId.load(getClass().getResourceAsStream("/fxml/buscarSeriesPantalla.fxml"));
            }
        } catch (Exception ex) {
            Logger.getLogger(Constantes.ERROR_CARGAR).log(Level.SEVERE, Constantes.NO_CARGAR, ex);
        }
    }

    private void cargarSeriesDetallada() {
        FXMLLoader fxmlLoaderSeriesDetallada = new FXMLLoader();
        try {
            if (paneSeriesDetalladaPantalla == null) {
                paneSeriesDetalladaPantalla = fxmlLoaderSeriesDetallada.load(getClass().getResourceAsStream("/fxml/seriesMasInformacionPantalla.fxml"));
            }
        } catch (Exception ex) {
            Logger.getLogger(Constantes.ERROR_CARGAR).log(Level.SEVERE, Constantes.NO_CARGAR, ex);
        }
    }

    //Una vez inicializadas, las pasamos al principal.

    private void pasarInicio() {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        if (paneInicioPantalla == null) {
            alerta.setContentText(Constantes.NO_PASA);
            alerta.showAndWait();
        } else {
            principal.setCenter(paneInicioPantalla);
        }
    }

    private void pasarPelisPorId() {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        if (panePelisPorIdPantalla == null) {
            alerta.setContentText(Constantes.NO_PASA);
            alerta.showAndWait();
        } else {
            principal.setCenter(panePelisPorIdPantalla);
        }
    }

    private void pasarPelisPopulares() {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        if (panePelisPopularesPantalla == null) {
            alerta.setContentText(Constantes.NO_PASA);
            alerta.showAndWait();
        } else {
            principal.setCenter(panePelisPopularesPantalla);
        }
    }

    private void pasarPelisColecciones() {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        if (panePelisColeccionesPantalla == null) {
            alerta.setContentText(Constantes.NO_PASA);
            alerta.showAndWait();
        } else {
            principal.setCenter(panePelisColeccionesPantalla);
        }
    }

    private void pasarEncontrarPersona() {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        if (paneEncontrarPersonasPantalla == null) {
            alerta.setContentText(Constantes.NO_PASA);
            alerta.showAndWait();
        } else {
            principal.setCenter(paneEncontrarPersonasPantalla);
        }
    }

    private void pasarSerieId() {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        if (paneSeriesEncontrarIdPantalla == null) {
            alerta.setContentText(Constantes.NO_PASA);
            alerta.showAndWait();
        } else {
            principal.setCenter(paneSeriesEncontrarIdPantalla);
        }
    }

    private void pasarSerieDetallada() {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        if (paneSeriesDetalladaPantalla == null) {
            alerta.setContentText(Constantes.NO_PASA);
            alerta.showAndWait();
        } else {
            principal.setCenter(paneSeriesDetalladaPantalla);
        }
    }

    //Ahora configuramos los botones para ir a dicho lugar. Si tuvieramos que cargar antes información, se haría aquí.
    @FXML
    private void irInicio() {
        pasarInicio();
    }

    @FXML
    private void irPelisPorId() {
        pasarPelisPorId();
    }

    @FXML
    private void irPelisPopulares() {
        pasarPelisPopulares();
    }

    @FXML
    private void irVerColecciones() {
        pasarPelisColecciones();
    }

    @FXML
    private void irEncontrarSeriesId() {
        pasarSerieId();
    }

    @FXML
    private void irInfoSeriesDetallada() {
        pasarSerieDetallada();
    }

    @FXML
    private void irEncontrarPersonas() {
        pasarEncontrarPersona();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Hay que hacer las cargas de las pantallas.
        cargarInicio();
        cargarPelisPorId();
        cargarPelisPopulares();
        cargarPelisColecciones();
        cargarEncontrarPersona();
        cargarSeriesPorId();
        cargarSeriesDetallada();
        //Y si queremos poner una de inicio aquí, es el momento.
        pasarInicio();
    }
}
