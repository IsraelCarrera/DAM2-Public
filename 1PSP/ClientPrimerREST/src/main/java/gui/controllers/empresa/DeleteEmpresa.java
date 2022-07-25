package gui.controllers.empresa;

import dao.data.ApiError;
import dao.data.Empresa;
import gui.controllers.PantallaPrincipal;
import gui.utils.Constantes;
import io.vavr.control.Either;
import io.vavr.control.Try;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import servicios.EmpresaServicios;

import javax.inject.Inject;
import java.util.List;

public class DeleteEmpresa {
    @FXML
    private ListView<Empresa> lvEmpresas;

    private PantallaPrincipal pantallaPrincipal;

    private final EmpresaServicios es;

    @Inject
    public DeleteEmpresa(EmpresaServicios es) {
        this.es = es;
    }

    public void setPrincipalPantalla(PantallaPrincipal pantallaPrincipal) {
        this.pantallaPrincipal = pantallaPrincipal;
    }

    @FXML
    private void borrarEmpresa() {
        if (lvEmpresas.getSelectionModel().getSelectedItem() != null) {
            Empresa e = lvEmpresas.getSelectionModel().getSelectedItem();
            if (e.getTrabajadores().isEmpty()) {
                var tarea = new Task<Either<ApiError, Boolean>>() {
                    @Override
                    protected Either<ApiError, Boolean> call() {
                        return es.deleteEmpresa(e.getId());
                    }
                };
                tarea.setOnSucceeded(workerStateEvent -> {
                    Try.of(() -> tarea.get().peek(s -> actualizarDespuDelete(s, e))
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
            } else {
                pantallaPrincipal.mandarAlert(Alert.AlertType.ERROR, Constantes.EMPRESA_CON_TRABAJADORES_BORRA_ANTES_A_LOS_TRABAJADORES);
            }
        } else {
            pantallaPrincipal.mandarAlert(Alert.AlertType.ERROR, Constantes.NO_HAS_SELECCIONADO_NINGUNA_EMPRESA);
        }
    }

    public void loadEmpresas() {
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

    private void actualizarDespuDelete(Boolean esBorrada, Empresa e) {
        if (esBorrada) {
            lvEmpresas.getItems().remove(e);
            this.pantallaPrincipal.mandarAlert(Alert.AlertType.INFORMATION, Constantes.EMPRESA_BORRADA_CON_EXITO);
        } else {
            this.pantallaPrincipal.mandarAlert(Alert.AlertType.ERROR, Constantes.NO_BORRADA_PERO_LLAMADA_REALIZADA_CON_EXITO);
        }
    }
}
