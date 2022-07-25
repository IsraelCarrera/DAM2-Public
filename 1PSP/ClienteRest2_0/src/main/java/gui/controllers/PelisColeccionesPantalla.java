package gui.controllers;

import io.vavr.control.Either;
import io.vavr.control.Try;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import modelos.Pelicula;
import servicios.PeliculasServicios;
import utils.Constantes;

import javax.inject.Inject;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PelisColeccionesPantalla implements Initializable {
    //FXML
    @FXML
    private ListView<Pelicula> lvPeliBuscador;
    @FXML
    private TextField tfIdPelicula;
    @FXML
    private ListView<Pelicula> lvPelicula;
    @FXML
    private Button btnVerColecciones;
    @FXML
    private ListView<Pelicula> lvColecciones;

    private PrincipalPantalla principalPantalla;

    public void setPrincipalPantalla(PrincipalPantalla principalPantalla) {
        this.principalPantalla = principalPantalla;
    }

    //Inyectar
    private final PeliculasServicios ps;

    @Inject
    public PelisColeccionesPantalla(PeliculasServicios ps) {
        this.ps = ps;
    }

    @FXML
    private void buscarPelicula() {
        String name = tfIdPelicula.getText();
        if (!name.isEmpty()) {
            var tarea = new Task<Either<String, List<Pelicula>>>() {
                @Override
                protected Either<String, List<Pelicula>> call() {
                    return ps.buscarPelicula(name);
                }
            };
            tarea.setOnSucceeded(workerStateEvent -> {
                Try.of(() -> tarea.get().peek(this::mostrarDatosPelis)
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
        } else {
            principalPantalla.mandarAlert(Alert.AlertType.ERROR, Constantes.NO_ESCRITO_NADA);
        }
    }

    @FXML
    private void btnCargarPeliSelec() {
        if (lvPeliBuscador.getSelectionModel().getSelectedItem() != null) {
            int id = lvPeliBuscador.getSelectionModel().getSelectedItem().getId();
            var tarea = new Task<Either<String, Pelicula>>() {
                @Override
                protected Either<String, Pelicula> call() {
                    return ps.peliculaPorId(id);
                }
            };
            tarea.setOnSucceeded(workerStateEvent -> {
                Try.of(() -> tarea.get().peek(this::mostrarPeli)
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
        } else {
            principalPantalla.mandarAlert(Alert.AlertType.ERROR, Constantes.NO_SELECCIONADO);
        }
    }

    @FXML
    private void mostrarColeciones() {
        var tarea = new Task<Either<String, List<Pelicula>>>() {
            @Override
            protected Either<String, List<Pelicula>> call() {
                return ps.coleccionPelis(lvPelicula.getItems().get(0).getBelongsToCollection().getId());
            }
        };
        tarea.setOnSucceeded(workerStateEvent -> {
            Try.of(() -> tarea.get().peek(this::mostrarDatosColecciones)
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
        limpiar();
        tfIdPelicula.clear();
        lvPeliBuscador.getItems().clear();
    }

    private void limpiar() {
        lvColecciones.getItems().clear();
        lvPelicula.getItems().clear();
        btnVerColecciones.setVisible(false);
        lvColecciones.setVisible(false);
    }

    private void mostrarDatosColecciones(List<Pelicula> resultado) {
        lvColecciones.getItems().clear();
        lvColecciones.getItems().addAll(resultado);
    }

    private void mostrarDatosPelis(List<Pelicula> resultado) {
        lvPeliBuscador.getItems().clear();
        lvPeliBuscador.getItems().addAll(resultado);
    }

    private void mostrarPeli(Pelicula resultado) {
        limpiar();
        lvPelicula.getItems().add(resultado);
        if (resultado.getBelongsToCollection() != null) {
            btnVerColecciones.setVisible(true);
            lvColecciones.setVisible(true);
        } else {
            btnVerColecciones.setVisible(false);
            lvColecciones.setVisible(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnVerColecciones.setVisible(false);
        lvColecciones.setVisible(false);
    }
}
