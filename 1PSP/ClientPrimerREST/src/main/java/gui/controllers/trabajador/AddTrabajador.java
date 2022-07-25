package gui.controllers.trabajador;

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
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import servicios.EmpresaServicios;
import servicios.TrabajadoresServicios;

import javax.inject.Inject;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AddTrabajador implements Initializable {
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
    @FXML
    private ListView<Empresa> lvEmpresas;

    private PantallaPrincipal pantallaPrincipal;

    private final TrabajadoresServicios ts;
    private final EmpresaServicios es;

    @Inject
    public AddTrabajador(TrabajadoresServicios ts, EmpresaServicios es) {
        this.ts = ts;
        this.es = es;
    }

    public void setPrincipalPantalla(PantallaPrincipal pantallaPrincipal) {
        this.pantallaPrincipal = pantallaPrincipal;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void addTrabajador() {
        if (lvEmpresas.getSelectionModel().getSelectedItem() != null) {
            Trabajador t = crearTrabajador();
            var tarea = new Task<Either<ApiError, Trabajador>>() {
                @Override
                protected Either<ApiError, Trabajador> call() {
                    return ts.addTrabajador(t);
                }
            };
            tarea.setOnSucceeded(workerStateEvent -> {
                Try.of(() -> tarea.get().peek(p -> this.pantallaPrincipal.mandarAlert(Alert.AlertType.INFORMATION, Constantes.TRABAJADOR_AGREGADO_CON_EXITO))
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

    private Trabajador crearTrabajador() {
        return new Trabajador(tfDni.getText(), tfNombre.getText(), tfDireccion.getText(), tfFecha.getValue(),
                tfPuestoTrabajo.getText(), lvEmpresas.getSelectionModel().getSelectedItem().getId());
    }
}
