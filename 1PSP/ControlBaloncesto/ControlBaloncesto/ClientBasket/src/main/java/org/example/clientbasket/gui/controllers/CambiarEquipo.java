package org.example.clientbasket.gui.controllers;


import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.example.clientbasket.gui.utils.Constantes;
import org.example.clientbasket.servicios.ServiciosEquipo;
import org.example.common.modelo.Equipo;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;

import javax.inject.Inject;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CambiarEquipo implements Initializable {
    private final ServiciosEquipo se;
    @FXML
    private ListView<Equipo> lvEquipo;
    @FXML
    private TextField tfNombreEquipo;
    //Pantalla principal
    private Principal principal;

    @Inject
    public CambiarEquipo(ServiciosEquipo se) {
        this.se = se;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }

    @FXML
    private void pasarDatos() {
        if (lvEquipo.getSelectionModel().getSelectedItem() != null) {
            tfNombreEquipo.setText(lvEquipo.getSelectionModel().getSelectedItem().getNombreEquipo());
        }
    }

    @FXML
    private void addEquipo() {
        String nombreEquipo = tfNombreEquipo.getText().toLowerCase();
        if (!nombreEquipo.isBlank()) {
            Equipo equipo = Equipo.builder().nombreEquipo(nombreEquipo).build();
            Single<Either<String, Equipo>> singleEquipo = Single.fromCallable(() -> se.addEquipo(equipo))
                    .subscribeOn(Schedulers.io())
                    .observeOn(JavaFxScheduler.platform())
                    .doFinally(() -> principal.getPrincipal().setCursor(Cursor.DEFAULT));

            singleEquipo.subscribe(either -> either
                            .peek(e -> {
                                principal.mandarAlert(Alert.AlertType.INFORMATION, Constantes.EQUIPO_AGREGADO_CORRECTAMENTE);
                                lvEquipo.getItems().add(e);
                            })
                            .peekLeft(fail -> principal.mandarAlert(Alert.AlertType.ERROR, fail)),
                    throwable -> principal.mandarAlert(Alert.AlertType.WARNING, throwable.getMessage()));

            principal.getPrincipal().setCursor(Cursor.WAIT);
        } else {
            principal.mandarAlert(Alert.AlertType.WARNING, Constantes.NO_HAY_NINGUN_NOMBRE_ESCRITO);
        }
    }

    @FXML
    private void updateEquipo() {
        String nombreEquipo = tfNombreEquipo.getText().toLowerCase();
        if (!nombreEquipo.isBlank() && lvEquipo.getSelectionModel().getSelectedItem() != null) {
            Equipo equipoViejo = lvEquipo.getSelectionModel().getSelectedItem();
            Single<Either<String, Equipo>> singleEquipo = Single.fromCallable(() -> se.updateEquipo(equipoViejo, nombreEquipo))
                    .subscribeOn(Schedulers.io())
                    .observeOn(JavaFxScheduler.platform())
                    .doFinally(() -> principal.getPrincipal().setCursor(Cursor.DEFAULT));

            singleEquipo.subscribe(either -> either
                            .peek(e -> {
                                principal.mandarAlert(Alert.AlertType.INFORMATION, Constantes.EQUIPO_MODIFICADO_CORRECTAMENTE);
                                lvEquipo.getItems().set(lvEquipo.getSelectionModel().getSelectedIndex(), e);
                            })
                            .peekLeft(fail -> principal.mandarAlert(Alert.AlertType.ERROR, fail)),
                    throwable -> principal.mandarAlert(Alert.AlertType.WARNING, throwable.getMessage()));

            principal.getPrincipal().setCursor(Cursor.WAIT);
        } else {
            principal.mandarAlert(Alert.AlertType.WARNING, Constantes.NO_HAY_NINGUN_NOMBRE_ESCRITO_Y_O_SELECCIONADO);
        }
    }

    @FXML
    private void deleteEquipo() {
        if (lvEquipo.getSelectionModel().getSelectedItem() != null) {
            Single<Either<String, Equipo>> singleEquipo = Single.fromCallable(() -> se.deleteEquipo(lvEquipo.getSelectionModel().getSelectedItem().getNombreEquipo()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(JavaFxScheduler.platform())
                    .doFinally(() -> principal.getPrincipal().setCursor(Cursor.DEFAULT));

            singleEquipo.subscribe(either -> either
                            .peek(e -> {
                                principal.mandarAlert(Alert.AlertType.INFORMATION, Constantes.EQUIPO_BORRADO_CORRECTAMENTE);
                                lvEquipo.getItems().remove(lvEquipo.getSelectionModel().getSelectedItem());
                                tfNombreEquipo.setText(null);
                            })
                            .peekLeft(fail -> principal.mandarAlert(Alert.AlertType.ERROR, fail)),
                    throwable -> principal.mandarAlert(Alert.AlertType.WARNING, throwable.getMessage()));

            principal.getPrincipal().setCursor(Cursor.WAIT);
        } else {
            principal.mandarAlert(Alert.AlertType.WARNING, Constantes.NO_HAY_NINGUN_EQUIPO_SELECCIONADO);
        }
    }

    public void cargarCambiarEquipo() {
        Single<Either<String, List<Equipo>>> singleEquipo = Single.fromCallable(se::getAllEquipos)
                .subscribeOn(Schedulers.io())
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> principal.getPrincipal().setCursor(Cursor.DEFAULT));

        singleEquipo.subscribe(either -> either
                        .peek(this::cargarListView)
                        .peekLeft(fail -> principal.mandarAlert(Alert.AlertType.ERROR, fail)),
                throwable -> principal.mandarAlert(Alert.AlertType.WARNING, throwable.getMessage()));

        principal.getPrincipal().setCursor(Cursor.WAIT);
    }

    private void cargarListView(List<Equipo> equipos) {
        lvEquipo.getItems().clear();
        lvEquipo.getItems().addAll(equipos);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
