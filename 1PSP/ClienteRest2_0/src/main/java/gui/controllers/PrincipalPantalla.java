package gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import lombok.extern.log4j.Log4j2;
import utils.Constantes;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

@Log4j2
public class PrincipalPantalla implements Initializable {
    @FXML
    private BorderPane principal;

    public BorderPane getPrincipal() {
        return principal;
    }

    //Pantallas que utilizaremos.
    //Inicio
    private AnchorPane paneInicioPantalla;
    private AnchorPane panePelisPorIdPantalla;
    private AnchorPane panePelisPopularesPantalla;
    private AnchorPane panePelisColeccionesPantalla;
    private AnchorPane paneEncontrarPersonasPantalla;
    private AnchorPane paneSeriesEncontrarIdPantalla;
    private AnchorPane paneSeriesDetalladaPantalla;

    private final FXMLLoader fxmlLoaderInicio;
    private final FXMLLoader fxmlLoaderPelisPorId;
    private final FXMLLoader fxmlLoaderPelisPopulares;
    private final FXMLLoader fxmlLoaderPelisColecciones;
    private final FXMLLoader fxmlLoaderEncontrarPersonas;
    private final FXMLLoader fxmlLoaderSeriesEncontrarId;
    private final FXMLLoader fxmlLoaderSeriesDetallada;

    @Inject
    public PrincipalPantalla(FXMLLoader fxmlLoaderInicio, FXMLLoader fxmlLoaderPelisPorId, FXMLLoader fxmlLoaderPelisPopulares,
                             FXMLLoader fxmlLoaderPelisColecciones, FXMLLoader fxmlLoaderEncontrarPersonas, FXMLLoader fxmlLoaderSeriesEncontrarId,
                             FXMLLoader fxmlLoaderSeriesDetallada) {
        this.fxmlLoaderInicio = fxmlLoaderInicio;
        this.fxmlLoaderPelisPorId = fxmlLoaderPelisPorId;
        this.fxmlLoaderPelisPopulares = fxmlLoaderPelisPopulares;
        this.fxmlLoaderPelisColecciones = fxmlLoaderPelisColecciones;
        this.fxmlLoaderEncontrarPersonas = fxmlLoaderEncontrarPersonas;
        this.fxmlLoaderSeriesEncontrarId = fxmlLoaderSeriesEncontrarId;
        this.fxmlLoaderSeriesDetallada = fxmlLoaderSeriesDetallada;
    }

    private BuscarPeliculaPantalla buscarPeliculaPantalla;
    private BuscarSeriesPantalla buscarSeriesPantalla;
    private PelisColeccionesPantalla pelisColeccionesPantalla;
    private PelisPopularesPantalla pelisPopularesPantalla;
    private PersonasEncontrarPantalla personasEncontrarPantalla;
    private SeriesMasInformacionPantalla seriesMasInformacionPantalla;

    //Ahora cargamos las pantallas

    private void cargarInicio() {
        try {
            if (paneInicioPantalla == null) {
                paneInicioPantalla = fxmlLoaderInicio.load(getClass().getResourceAsStream("/fxml/inicioPantalla.fxml"));
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    private void cargarPelisPorId() {
        try {
            if (panePelisPorIdPantalla == null) {
                panePelisPorIdPantalla = fxmlLoaderPelisPorId.load(getClass().getResourceAsStream("/fxml/buscarPeliculaPantalla.fxml"));
                buscarPeliculaPantalla = fxmlLoaderPelisPorId.getController();
                buscarPeliculaPantalla.setPrincipalPantalla(this);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    private void cargarPelisPopulares() {
        try {
            if (panePelisPopularesPantalla == null) {
                panePelisPopularesPantalla = fxmlLoaderPelisPopulares.load(getClass().getResourceAsStream("/fxml/pelisPopularesPantalla.fxml"));
                pelisPopularesPantalla = fxmlLoaderPelisPopulares.getController();
                pelisPopularesPantalla.setPrincipalPantalla(this);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    private void cargarPelisColecciones() {
        try {
            if (panePelisColeccionesPantalla == null) {
                panePelisColeccionesPantalla = fxmlLoaderPelisColecciones.load(getClass().getResourceAsStream("/fxml/pelisColeccionesPantalla.fxml"));
                pelisColeccionesPantalla = fxmlLoaderPelisColecciones.getController();
                pelisColeccionesPantalla.setPrincipalPantalla(this);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    private void cargarEncontrarPersona() {
        try {
            if (paneEncontrarPersonasPantalla == null) {
                paneEncontrarPersonasPantalla = fxmlLoaderEncontrarPersonas.load(getClass().getResourceAsStream("/fxml/PersonasEncontrarPantalla.fxml"));
                personasEncontrarPantalla = fxmlLoaderEncontrarPersonas.getController();
                personasEncontrarPantalla.setPrincipalPantalla(this);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    private void cargarSeriesPorId() {
        try {
            if (paneSeriesEncontrarIdPantalla == null) {
                paneSeriesEncontrarIdPantalla = fxmlLoaderSeriesEncontrarId.load(getClass().getResourceAsStream("/fxml/buscarSeriesPantalla.fxml"));
                buscarSeriesPantalla = fxmlLoaderSeriesEncontrarId.getController();
                buscarSeriesPantalla.setPrincipalPantalla(this);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    private void cargarSeriesDetallada() {
        try {
            if (paneSeriesDetalladaPantalla == null) {
                paneSeriesDetalladaPantalla = fxmlLoaderSeriesDetallada.load(getClass().getResourceAsStream("/fxml/seriesMasInformacionPantalla.fxml"));
                seriesMasInformacionPantalla = fxmlLoaderSeriesDetallada.getController();
                seriesMasInformacionPantalla.setPrincipalPantalla(this);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    //Una vez inicializadas, las pasamos al principal.

    private void pasarInicio() {
        if (paneInicioPantalla == null) {
            mandarAlert(Alert.AlertType.ERROR, Constantes.NO_PASA);
        } else {
            principal.setCenter(paneInicioPantalla);
        }
    }

    private void pasarPelisPorId() {
        if (panePelisPorIdPantalla == null) {
            mandarAlert(Alert.AlertType.ERROR, Constantes.NO_PASA);
        } else {
            principal.setCenter(panePelisPorIdPantalla);
        }
    }

    private void pasarPelisPopulares() {
        if (panePelisPopularesPantalla == null) {
            mandarAlert(Alert.AlertType.ERROR, Constantes.NO_PASA);
        } else {
            principal.setCenter(panePelisPopularesPantalla);
        }
    }

    private void pasarPelisColecciones() {
        if (panePelisColeccionesPantalla == null) {
            mandarAlert(Alert.AlertType.ERROR, Constantes.NO_PASA);
        } else {
            principal.setCenter(panePelisColeccionesPantalla);
        }
    }

    private void pasarEncontrarPersona() {
        if (paneEncontrarPersonasPantalla == null) {
            mandarAlert(Alert.AlertType.ERROR, Constantes.NO_PASA);
        } else {
            principal.setCenter(paneEncontrarPersonasPantalla);
        }
    }

    private void pasarSerieId() {
        if (paneSeriesEncontrarIdPantalla == null) {
            mandarAlert(Alert.AlertType.ERROR, Constantes.NO_PASA);
        } else {
            principal.setCenter(paneSeriesEncontrarIdPantalla);
        }
    }

    private void pasarSerieDetallada() {
        if (paneSeriesDetalladaPantalla == null) {
            mandarAlert(Alert.AlertType.ERROR, Constantes.NO_PASA);
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

    //Función de los alerts para que estén todos juntos.

    public void mandarAlert(Alert.AlertType tipoAlerta, String aviso) {
        Alert alerta = new Alert(tipoAlerta);
        alerta.setContentText(aviso);
        alerta.showAndWait();
    }
}
