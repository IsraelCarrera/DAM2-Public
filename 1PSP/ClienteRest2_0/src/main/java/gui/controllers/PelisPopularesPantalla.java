package gui.controllers;

import io.vavr.control.Either;
import io.vavr.control.Try;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import modelos.Pelicula;
import servicios.PeliculasServicios;

import javax.inject.Inject;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PelisPopularesPantalla implements Initializable {
    //FXML
    @FXML
    private ComboBox<Integer> cbSelectorPag;
    @FXML
    private ListView<Pelicula> lvPeliculas;

    private PrincipalPantalla principalPantalla;

    public void setPrincipalPantalla(PrincipalPantalla principalPantalla) {
        this.principalPantalla = principalPantalla;
    }

    //Inyectar
    private final PeliculasServicios ps;

    @Inject
    public PelisPopularesPantalla(PeliculasServicios ps) {
        this.ps = ps;
    }

    @FXML
    private void btCargar() {
        int paginaSelec = cbSelectorPag.getSelectionModel().getSelectedItem();
        var tarea = new Task<Either<String, List<Pelicula>>>() {
            @Override
            protected Either<String, List<Pelicula>> call() {
                return ps.peliculasPopulares(paginaSelec);
            }
        };
        tarea.setOnSucceeded(workerStateEvent -> {
            Try.of(() -> tarea.get().peek(this::meterPelis)
                            .peekLeft(s -> principalPantalla.mandarAlert(Alert.AlertType.WARNING, s)))
                    .onFailure(t -> principalPantalla.mandarAlert(Alert.AlertType.ERROR, t.getMessage()));
            this.principalPantalla.getPrincipal().setCursor(Cursor.DEFAULT);
        });
        tarea.setOnFailed(workerStateEvent -> {
            principalPantalla.mandarAlert(Alert.AlertType.ERROR, workerStateEvent.getSource().getException().getMessage());
            this.principalPantalla.getPrincipal().setCursor(Cursor.DEFAULT);
        });
        new Thread(tarea).start();
        this.principalPantalla.getPrincipal().setCursor(Cursor.WAIT);
    }

    @FXML
    private void btnLimpiar() {
        lvPeliculas.getItems().clear();
        cbSelectorPag.getSelectionModel().clearSelection();
    }

    private void meterPelis(List<Pelicula> resultado) {
        lvPeliculas.getItems().clear();
        lvPeliculas.getItems().addAll(resultado);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (int i = 1; i <= 10; i++) {
            cbSelectorPag.getItems().add(i);
        }
    }
}
