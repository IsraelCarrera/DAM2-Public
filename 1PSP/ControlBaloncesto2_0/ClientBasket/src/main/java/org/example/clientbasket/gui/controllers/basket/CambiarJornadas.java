package org.example.clientbasket.gui.controllers.basket;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.example.clientbasket.gui.controllers.Principal;
import org.example.clientbasket.gui.utils.Constantes;
import org.example.clientbasket.servicios.ServiciosJornada;
import org.example.common.modelo.Jornada;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;

import javax.inject.Inject;
import java.net.URL;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class CambiarJornadas implements Initializable {
    private final ServiciosJornada sj;
    @FXML
    private TextField tfIdJornada;
    @FXML
    private ListView<Jornada> lvJornada;
    @FXML
    private DatePicker dpFechaJornada;
    //Pantalla principal
    private Principal principal;

    @Inject
    public CambiarJornadas(ServiciosJornada sj) {
        this.sj = sj;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }

    @FXML
    private void pasarDatos() {
        if (lvJornada.getSelectionModel().getSelectedItem() != null) {
            tfIdJornada.setText(String.valueOf(lvJornada.getSelectionModel().getSelectedItem().getIdJornada()));
            dpFechaJornada.setValue(lvJornada.getSelectionModel().getSelectedItem().getFechaJornada());
        }
    }

    @FXML
    private void addJornada() {
        try {
            LocalDate fecha = dpFechaJornada.getValue();
            Jornada jornada = Jornada.builder().fechaJornada(fecha).build();
            Single<Either<String, Jornada>> singleJornadas = sj.addJornada(jornada)
                    .observeOn(JavaFxScheduler.platform())
                    .doFinally(() -> principal.getPrincipal().setCursor(Cursor.DEFAULT));

            singleJornadas.subscribe(either -> either
                            .peek(j -> {
                                principal.mandarAlert(Alert.AlertType.INFORMATION, Constantes.JORNADA_ADD_CORRECTAMENTE);
                                lvJornada.getItems().add(j);
                            })
                            .peekLeft(fail -> principal.mandarAlert(Alert.AlertType.ERROR, fail)),
                    throwable -> principal.mandarAlert(Alert.AlertType.WARNING, throwable.getMessage()));
            principal.getPrincipal().setCursor(Cursor.WAIT);
        } catch (DateTimeException e) {
            principal.mandarAlert(Alert.AlertType.WARNING, Constantes.NO_HAS_INTRODUCIDO_UNA_FECHA);
        }
    }

    @FXML
    private void updateJornada() {
        try {
            LocalDate fecha = dpFechaJornada.getValue();
            if (lvJornada.getSelectionModel().getSelectedItem() != null) {
                Single<Either<String, Jornada>> singleJornadas =sj.updateJornada(lvJornada.getSelectionModel().getSelectedItem(), fecha)
                        .observeOn(JavaFxScheduler.platform())
                        .doFinally(() -> principal.getPrincipal().setCursor(Cursor.DEFAULT));

                singleJornadas.subscribe(either -> either
                                .peek(j -> {
                                    principal.mandarAlert(Alert.AlertType.INFORMATION, Constantes.JORNADA_MODIFICADA_CORRECTAMENTE);
                                    lvJornada.getItems().set(lvJornada.getSelectionModel().getSelectedIndex(), j);
                                })
                                .peekLeft(fail -> principal.mandarAlert(Alert.AlertType.ERROR, fail)),
                        throwable -> principal.mandarAlert(Alert.AlertType.WARNING, throwable.getMessage()));
                principal.getPrincipal().setCursor(Cursor.WAIT);
            } else {
                principal.mandarAlert(Alert.AlertType.WARNING, Constantes.NO_HA_SELECCIONADO_NINGUNA_JORNADA);
            }
        } catch (DateTimeException e) {
            principal.mandarAlert(Alert.AlertType.WARNING, Constantes.NO_HAS_INTRODUCIDO_UNA_FECHA);
        }
    }

    @FXML
    private void deleteJornada() {
        if (lvJornada.getSelectionModel().getSelectedItem() != null) {
            Single<Either<String, Integer>> singleJornadas = sj.deleteJornada(lvJornada.getSelectionModel().getSelectedItem().getIdJornada())
                    .observeOn(JavaFxScheduler.platform())
                    .doFinally(() -> principal.getPrincipal().setCursor(Cursor.DEFAULT));

            singleJornadas.subscribe(either -> either
                            .peek(j -> {
                                principal.mandarAlert(Alert.AlertType.INFORMATION, Constantes.JORNADA_BORRADA_CORRECTAMENTE);
                                lvJornada.getItems().remove(lvJornada.getSelectionModel().getSelectedItem());
                            })
                            .peekLeft(fail -> principal.mandarAlert(Alert.AlertType.ERROR, fail)),
                    throwable -> principal.mandarAlert(Alert.AlertType.WARNING, throwable.getMessage()));
            principal.getPrincipal().setCursor(Cursor.WAIT);
        } else {
            principal.mandarAlert(Alert.AlertType.WARNING, Constantes.NO_HA_SELECCIONADO_NINGUNA_JORNADA);
        }
    }

    public void cargarCambiarJornadas() {
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
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tfIdJornada.setEditable(false);
    }
}
