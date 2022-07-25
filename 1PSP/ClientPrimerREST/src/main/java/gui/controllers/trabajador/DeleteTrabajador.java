package gui.controllers.trabajador;

import dao.data.ApiError;
import dao.data.Trabajador;
import gui.controllers.PantallaPrincipal;
import gui.utils.Constantes;
import io.vavr.control.Either;
import io.vavr.control.Try;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import servicios.TrabajadoresServicios;

import javax.inject.Inject;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DeleteTrabajador implements Initializable {
    @FXML
    private ListView<Trabajador> lvTrabajador;

    private PantallaPrincipal pantallaPrincipal;

    private final TrabajadoresServicios ts;

    @Inject
    public DeleteTrabajador(TrabajadoresServicios ts) {
        this.ts = ts;
    }


    public void setPrincipalPantalla(PantallaPrincipal pantallaPrincipal) {
        this.pantallaPrincipal = pantallaPrincipal;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void deleteTrabajador() {
        if (lvTrabajador.getSelectionModel().getSelectedItem() != null) {
            Trabajador t = lvTrabajador.getSelectionModel().getSelectedItem();
            var tarea = new Task<Either<ApiError, Boolean>>() {
                @Override
                protected Either<ApiError, Boolean> call() {
                    return ts.deleteTrabajador(t.getId());
                }
            };
            tarea.setOnSucceeded(workerStateEvent -> {
                Try.of(() -> tarea.get().peek(s -> actualizarDespuDelete(s, t))
                                .peekLeft(s -> pantallaPrincipal.mandarAlert(Alert.AlertType.WARNING, s.getMensaje())))
                        .onFailure(p -> pantallaPrincipal.mandarAlert(Alert.AlertType.ERROR, p.getMessage()));
                this.pantallaPrincipal.getPrincipal().setCursor(Cursor.DEFAULT);
            });
            tarea.setOnFailed(workerStateEvent -> {
                pantallaPrincipal.mandarAlert(Alert.AlertType.ERROR, workerStateEvent.getSource().getException().getMessage());
                pantallaPrincipal.getPrincipal().setCursor(Cursor.DEFAULT);
            });
            new Thread(tarea).start();
            this.pantallaPrincipal.getPrincipal().setCursor(Cursor.WAIT);
        }
    }

    public void loadTrabajadores() {
        var tarea = new Task<Either<ApiError, List<Trabajador>>>() {
            @Override
            protected Either<ApiError, List<Trabajador>> call() {
                return ts.getAllTrabajadores();
            }
        };
        tarea.setOnSucceeded(workerStateEvent -> {
            Try.of(() -> tarea.get().peek(this::meterTrabajadores)
                            .peekLeft(s -> pantallaPrincipal.mandarAlert(Alert.AlertType.WARNING, s.getMensaje())))
                    .onFailure(t -> pantallaPrincipal.mandarAlert(Alert.AlertType.ERROR, t.getMessage()));
            this.pantallaPrincipal.getPrincipal().setCursor(Cursor.DEFAULT);
        });
        tarea.setOnFailed(workerStateEvent -> {
            pantallaPrincipal.mandarAlert(Alert.AlertType.ERROR, workerStateEvent.getSource().getException().getMessage());
            pantallaPrincipal.getPrincipal().setCursor(Cursor.DEFAULT);
        });
        new Thread(tarea).start();
        this.pantallaPrincipal.getPrincipal().setCursor(Cursor.WAIT);
    }

    private void meterTrabajadores(List<Trabajador> trabajadores) {
        lvTrabajador.getItems().clear();
        lvTrabajador.getItems().addAll(trabajadores);
    }

    private void actualizarDespuDelete(Boolean esBorrada, Trabajador t) {
        if (esBorrada) {
            lvTrabajador.getItems().remove(t);
            this.pantallaPrincipal.mandarAlert(Alert.AlertType.INFORMATION, Constantes.TRABAJADOR_BORRADO_CON_EXITO);
        } else {
            this.pantallaPrincipal.mandarAlert(Alert.AlertType.ERROR, Constantes.NO_BORRADA_PERO_LLAMADA_REALIZADA_CON_EXITO);
        }
    }
}
