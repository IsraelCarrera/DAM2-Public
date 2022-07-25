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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import servicios.TrabajadoresServicios;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class VerTrabajador implements Initializable {
    @FXML
    private Label lbId;
    @FXML
    private Label lbDni;
    @FXML
    private Label lbNombre;
    @FXML
    private Label lbDireccion;
    @FXML
    private Label lbFecha;
    @FXML
    private Label lbPuestoTrabajo;
    @FXML
    private Label lbEmpresa;
    @FXML
    private TextField tfBuscarTrabajadorId;

    private PantallaPrincipal pantallaPrincipal;

    private final TrabajadoresServicios ts;

    @Inject
    public VerTrabajador(TrabajadoresServicios ts) {
        this.ts = ts;
    }

    public void setPrincipalPantalla(PantallaPrincipal pantallaPrincipal) {
        this.pantallaPrincipal = pantallaPrincipal;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void buscarTrabajador() {
        try {
            int id = Integer.parseInt(tfBuscarTrabajadorId.getText());
            var tarea = new Task<Either<ApiError, Trabajador>>() {
                @Override
                protected Either<ApiError, Trabajador> call() {
                    return ts.getTrabajadorId(id);
                }
            };
            tarea.setOnSucceeded(workerStateEvent -> {
                Try.of(() -> tarea.get().peek(this::pintarTrabajador)
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

        } catch (Exception e) {
            pantallaPrincipal.mandarAlert(Alert.AlertType.WARNING, Constantes.NO_HAS_ESCRITO_UN_NUMERO);
        }
    }

    private void pintarTrabajador(Trabajador t) {
        lbId.setText(String.valueOf(t.getId()));
        lbDni.setText(t.getDni());
        lbNombre.setText(t.getNombre());
        lbDireccion.setText(t.getDireccion());
        lbFecha.setText(t.getFechaNacimiento().toString());
        lbPuestoTrabajo.setText(t.getPuestoTrabajo());
        lbEmpresa.setText(String.valueOf(t.getIdEmpresa()));
    }
}
