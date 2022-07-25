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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import servicios.TrabajadoresServicios;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdateTrabajador implements Initializable {
    @FXML
    private TextField tfIdTrabajador;
    @FXML
    private TextField tfDni;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfDireccion;
    @FXML
    private TextField tfPuestoTrabajo;
    @FXML
    private DatePicker tfFecha;

    private Trabajador trabajadorBuscado = null;

    private PantallaPrincipal pantallaPrincipal;

    private final TrabajadoresServicios ts;

    @Inject
    public UpdateTrabajador(TrabajadoresServicios ts) {
        this.ts = ts;
    }

    public void setPrincipalPantalla(PantallaPrincipal pantallaPrincipal) {
        this.pantallaPrincipal = pantallaPrincipal;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void updateTrabajador() {
        if (trabajadorBuscado != null) {
            Trabajador t = recogerDatos();
            var tarea = new Task<Either<ApiError, Trabajador>>() {
                @Override
                protected Either<ApiError, Trabajador> call() {
                    return ts.updateTrabajador(t);
                }
            };
            tarea.setOnSucceeded(workerStateEvent -> {
                Try.of(() -> tarea.get().peek(s -> pantallaPrincipal.mandarAlert(Alert.AlertType.INFORMATION, Constantes.EL_TRABAJADOR_HA_SIDO_CAMBIADO_CON_EXITO))
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
            borrar();
        } else {
            pantallaPrincipal.mandarAlert(Alert.AlertType.ERROR, Constantes.BUSCA_ANTES_UN_TRABAJADOR);
        }
    }

    @FXML
    private void buscarTrabajador() {
        try {
            int id = Integer.parseInt(tfIdTrabajador.getText());
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
            pantallaPrincipal.mandarAlert(Alert.AlertType.WARNING, "No has escrito un número.");
        }
    }

    private void pintarTrabajador(Trabajador t) {
        this.trabajadorBuscado = t;
        tfDni.setText(t.getDni());
        tfNombre.setText(t.getNombre());
        tfDireccion.setText(t.getDireccion());
        tfPuestoTrabajo.setText(t.getPuestoTrabajo());
        tfFecha.setValue(t.getFechaNacimiento());
    }

    private void borrar() {
        this.trabajadorBuscado = null;
        tfDni.setText("");
        tfNombre.setText("");
        tfDireccion.setText("");
        tfPuestoTrabajo.setText("");
        tfFecha.setValue(null);
        tfIdTrabajador.setText("");
    }

    private Trabajador recogerDatos() {
        return new Trabajador(tfPuestoTrabajo.getText(), tfFecha.getValue(), tfDireccion.getText()
                , trabajadorBuscado.getIdEmpresa(), trabajadorBuscado.getId(), tfNombre.getText(), tfDni.getText());
    }
}
