package gui.controllers.verAll;

import dao.data.ApiError;
import dao.data.Empresa;
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
import servicios.EmpresaServicios;
import servicios.TrabajadoresServicios;

import javax.inject.Inject;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PantallaVerTodo implements Initializable {
    @FXML
    private ListView<Empresa> lvEmpresas;
    @FXML
    private ListView<Trabajador> lvTrabajadores;

    private PantallaPrincipal pantallaPrincipal;

    private final EmpresaServicios es;
    private final TrabajadoresServicios ts;

    @Inject
    public PantallaVerTodo(EmpresaServicios es, TrabajadoresServicios ts) {
        this.es = es;
        this.ts = ts;
    }

    public void setPrincipalPantalla(PantallaPrincipal pantallaPrincipal) {
        this.pantallaPrincipal = pantallaPrincipal;
    }

    @FXML
    private void verTrabajadores() {
        if (lvEmpresas.getSelectionModel().getSelectedItem() != null) {
            var tarea = new Task<Either<ApiError, List<Trabajador>>>() {
                @Override
                protected Either<ApiError, List<Trabajador>> call() {
                    return ts.getAllTrabajadoresPorIdEmpresa(lvEmpresas.getSelectionModel().getSelectedItem().getId());
                }
            };
            tarea.setOnSucceeded(workerStateEvent -> {
                Try.of(() -> tarea.get().peek(this::meterTrabajadores)
                                .peekLeft(s -> pantallaPrincipal.mandarAlert(Alert.AlertType.WARNING, s.getMensaje())))
                        .onFailure(t -> pantallaPrincipal.mandarAlert(Alert.AlertType.ERROR, Constantes.NO_HAY_DATOS));
                this.pantallaPrincipal.getPrincipal().setCursor(Cursor.DEFAULT);
            });
            tarea.setOnFailed(workerStateEvent -> {
                pantallaPrincipal.mandarAlert(Alert.AlertType.ERROR, workerStateEvent.getSource().getException().getMessage());
                pantallaPrincipal.getPrincipal().setCursor(Cursor.DEFAULT);
            });
            new Thread(tarea).start();
            this.pantallaPrincipal.getPrincipal().setCursor(Cursor.WAIT);
        } else {
            pantallaPrincipal.mandarAlert(Alert.AlertType.ERROR, Constantes.NO_SELECCIONADO);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void preloadEmpresas() {
        var tarea = new Task<Either<ApiError, List<Empresa>>>() {
            @Override
            protected Either<ApiError, List<Empresa>> call() {
                return es.getAllEmpresas();
            }
        };
        tarea.setOnSucceeded(workerStateEvent -> {
            Try.of(() -> tarea.get().peek(this::meterEmpresas)
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

    private void meterEmpresas(List<Empresa> empresas) {
        lvEmpresas.getItems().clear();
        lvEmpresas.getItems().addAll(empresas);
    }

    private void meterTrabajadores(List<Trabajador> trabajadores) {
        lvTrabajadores.getItems().clear();
        lvTrabajadores.getItems().addAll(trabajadores);
    }

}
