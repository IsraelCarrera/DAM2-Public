package org.example.clientbasket.gui.controllers.basket;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.example.clientbasket.gui.controllers.Principal;
import org.example.clientbasket.gui.utils.Constantes;
import org.example.clientbasket.servicios.ServiciosPartido;
import org.example.common.modelo.Partido;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;

import javax.inject.Inject;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CambiarPartidos implements Initializable {
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
    private TextField tfPuntosEquipoLocal;
    @FXML
    private TextField tfPuntosEquipoVisitante;
    //Pantalla principal
    private Principal principal;

    @Inject
    public CambiarPartidos(ServiciosPartido sp) {
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
            tfPuntosEquipoLocal.setText(String.valueOf(lvPartidos.getSelectionModel().getSelectedItem().getPuntosEquipoLocal()));
            tfPuntosEquipoVisitante.setText(String.valueOf(lvPartidos.getSelectionModel().getSelectedItem().getPuntosEquipoVisitante()));
        }
    }

    @FXML
    private void borrarPartido() {
        if (lvPartidos.getSelectionModel().getSelectedItem() != null) {
            Single<Either<String, Partido>> singlePartido = sp.deletePartido(lvPartidos.getSelectionModel().getSelectedItem().getIdJornada(),
                            lvPartidos.getSelectionModel().getSelectedItem().getNombreEquipoLocal(), lvPartidos.getSelectionModel().getSelectedItem().getNombreEquipoVisitante())
                    .observeOn(JavaFxScheduler.platform())
                    .doFinally(() -> principal.getPrincipal().setCursor(Cursor.DEFAULT));
            singlePartido.subscribe(either -> either
                            .peek(p -> {
                                principal.mandarAlert(Alert.AlertType.INFORMATION, Constantes.PARTIDO_ELIMINADO_CORRECTAMENTE);
                                lvPartidos.getItems().remove(lvPartidos.getSelectionModel().getSelectedItem());
                            })
                            .peekLeft(fail -> principal.mandarAlert(Alert.AlertType.ERROR, fail)),
                    throwable -> principal.mandarAlert(Alert.AlertType.WARNING, throwable.getMessage()));
            principal.getPrincipal().setCursor(Cursor.WAIT);
        }
    }

    @FXML
    private void updatePartido() {
        try {
            int puntosEquipoVisitante = Integer.parseInt(tfPuntosEquipoVisitante.getText());
            int puntosEquipoLocal = Integer.parseInt(tfPuntosEquipoLocal.getText());
            if (lvPartidos.getSelectionModel().getSelectedItem() != null) {
                Single<Either<String, Partido>> singlePartido = sp.updatePartido(lvPartidos.getSelectionModel().getSelectedItem(),
                                puntosEquipoLocal, puntosEquipoVisitante)
                        .observeOn(JavaFxScheduler.platform())
                        .doFinally(() -> principal.getPrincipal().setCursor(Cursor.DEFAULT));

                singlePartido.subscribe(either -> either
                                .peek(p -> {
                                    principal.mandarAlert(Alert.AlertType.INFORMATION, Constantes.PARTIDO_MODIFICADO_CORRECTAMENTE);
                                    lvPartidos.getItems().set(lvPartidos.getSelectionModel().getSelectedIndex(), p);
                                })
                                .peekLeft(fail -> principal.mandarAlert(Alert.AlertType.ERROR, fail)),
                        throwable -> principal.mandarAlert(Alert.AlertType.WARNING, throwable.getMessage()));
                principal.getPrincipal().setCursor(Cursor.WAIT);
            }
        } catch (Exception e) {
            principal.mandarAlert(Alert.AlertType.ERROR, Constantes.NO_HA_ESCRITO_NUMEROS_EN_EL_CAMPO_DE_LOS_PUNTOS_ESCRIBA_NUMEROS);
        }
    }

    @FXML
    private void borrarDatos() {
        lbEquipoLocal.setText(null);
        lbEquipoVisitante.setText(null);
        lbJornada.setText(null);
        tfPuntosEquipoLocal.setText(null);
        tfPuntosEquipoVisitante.setText(null);
    }

    public void cargarCambiarPartidos() {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
