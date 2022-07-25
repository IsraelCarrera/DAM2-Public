package org.example.clientbasket.gui.controllers.basket;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.example.clientbasket.gui.controllers.Principal;
import org.example.clientbasket.gui.utils.Constantes;
import org.example.clientbasket.servicios.ServiciosEquipo;
import org.example.clientbasket.servicios.ServiciosJornada;
import org.example.clientbasket.servicios.ServiciosPartido;
import org.example.common.modelo.Equipo;
import org.example.common.modelo.Jornada;
import org.example.common.modelo.Partido;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;

import javax.inject.Inject;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class VerPartidos implements Initializable {
    private final ServiciosEquipo se;
    private final ServiciosJornada sj;
    private final ServiciosPartido sp;
    @FXML
    private ListView<Partido> lvPartidos;
    @FXML
    private Label lbEquipoLocal;
    @FXML
    private Label lbEquipoVisitante;
    @FXML
    private Label lbJornada;
    @FXML
    private Label lbPuntosLocal;
    @FXML
    private Label lbPuntosVisitante;
    @FXML
    private ListView<Jornada> lvJornada;
    @FXML
    private ListView<Equipo> lvEquipo;
    //Pantalla principal
    private Principal principal;

    @Inject
    public VerPartidos(ServiciosEquipo se, ServiciosJornada sj, ServiciosPartido sp) {
        this.se = se;
        this.sj = sj;
        this.sp = sp;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }

    @FXML
    private void mostrarDatos() {
        if (lvPartidos.getSelectionModel().getSelectedItem() != null) {
            lbEquipoLocal.setText(lvPartidos.getSelectionModel().getSelectedItem().getNombreEquipoLocal());
            lbEquipoVisitante.setText(lvPartidos.getSelectionModel().getSelectedItem().getNombreEquipoVisitante());
            lbJornada.setText(String.valueOf(lvPartidos.getSelectionModel().getSelectedItem().getIdJornada()));
            lbPuntosLocal.setText(String.valueOf(lvPartidos.getSelectionModel().getSelectedItem().getPuntosEquipoLocal()));
            lbPuntosVisitante.setText(String.valueOf(lvPartidos.getSelectionModel().getSelectedItem().getPuntosEquipoLocal()));
        }
    }

    @FXML
    private void buscarAllPartidos() {
        Single<Either<String, List<Partido>>> singlePartido = sp.getAllPartidos()
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> principal.getPrincipal().setCursor(Cursor.DEFAULT));

        singlePartido.subscribe(either -> either
                        .peek(partidos -> {
                            lvPartidos.getItems().clear();
                            lvPartidos.getItems().addAll(partidos);
                        })
                        .peekLeft(fail -> principal.mandarAlert(Alert.AlertType.ERROR, fail)),
                throwable -> principal.mandarAlert(Alert.AlertType.WARNING, throwable.getMessage()));
        principal.getPrincipal().setCursor(Cursor.WAIT);
    }

    @FXML
    private void buscarPorJornada() {
        if (lvJornada.getSelectionModel().getSelectedItem() != null) {
            Single<Either<String, List<Partido>>> singlePartido =
                    sp.getAllPartidosPorJornada(lvJornada.getSelectionModel().getSelectedItem().getIdJornada())
                            .observeOn(JavaFxScheduler.platform())
                            .doFinally(() -> principal.getPrincipal().setCursor(Cursor.DEFAULT));

            singlePartido.subscribe(either -> either
                            .peek(partidos -> {
                                lvPartidos.getItems().clear();
                                lvPartidos.getItems().addAll(partidos);
                            })
                            .peekLeft(fail -> principal.mandarAlert(Alert.AlertType.ERROR, fail)),
                    throwable -> principal.mandarAlert(Alert.AlertType.WARNING, throwable.getMessage()));
            principal.getPrincipal().setCursor(Cursor.WAIT);
        } else {
            principal.mandarAlert(Alert.AlertType.WARNING, Constantes.NO_HA_SELECCIONADO_NINGUNA_JORNADA);
        }
    }

    @FXML
    private void buscarPorEquipo() {
        if (lvEquipo.getSelectionModel().getSelectedItem() != null) {
            Single<Either<String, List<Partido>>> singlePartido =
                    sp.getAllPartidosPorEquipo(lvEquipo.getSelectionModel().getSelectedItem().getNombreEquipo())
                            .observeOn(JavaFxScheduler.platform())
                            .doFinally(() -> principal.getPrincipal().setCursor(Cursor.DEFAULT));

            singlePartido.subscribe(either -> either
                            .peek(partidos -> {
                                lvPartidos.getItems().clear();
                                lvPartidos.getItems().addAll(partidos);
                            })
                            .peekLeft(fail -> principal.mandarAlert(Alert.AlertType.ERROR, fail)),
                    throwable -> principal.mandarAlert(Alert.AlertType.WARNING, throwable.getMessage()));
            principal.getPrincipal().setCursor(Cursor.WAIT);
        } else {
            principal.mandarAlert(Alert.AlertType.WARNING, Constantes.NO_HA_SELECCIONADO_NINGUN_EQUIPO);
        }
    }

    @FXML
    private void limpiarPantalla() {
        lvPartidos.getItems().clear();
        lbEquipoLocal.setText(null);
        lbEquipoVisitante.setText(null);
        lbJornada.setText(null);
        lbPuntosLocal.setText(null);
        lbPuntosVisitante.setText(null);
        lvEquipo.getSelectionModel().clearSelection();
        lvJornada.getSelectionModel().clearSelection();
    }

    public void preCargaVerPartidos() {
        //Jornadas
        Single<Either<String, List<Jornada>>> singleJornadas = sj.getAllJornadas()
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> principal.getPrincipal().setCursor(Cursor.DEFAULT));

        singleJornadas.subscribe(either -> either
                        .peek(jornadas -> {
                            lvJornada.getItems().clear();
                            lvJornada.getItems().addAll(jornadas);
                        })
                        .peekLeft(fail -> principal.mandarAlert(Alert.AlertType.ERROR, fail)),
                throwable -> principal.mandarAlert(Alert.AlertType.WARNING, throwable.getMessage()));
        principal.getPrincipal().setCursor(Cursor.WAIT);

        //Partidos
        Single<Either<String, List<Equipo>>> singleEquipos = se.getAllEquipos()
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> principal.getPrincipal().setCursor(Cursor.DEFAULT));

        singleEquipos.subscribe(either -> either
                        .peek(equipos -> {
                            lvEquipo.getItems().clear();
                            lvEquipo.getItems().addAll(equipos);
                        })
                        .peekLeft(fail -> principal.mandarAlert(Alert.AlertType.ERROR, fail)),
                throwable -> principal.mandarAlert(Alert.AlertType.WARNING, throwable.getMessage()));

        principal.getPrincipal().setCursor(Cursor.WAIT);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
