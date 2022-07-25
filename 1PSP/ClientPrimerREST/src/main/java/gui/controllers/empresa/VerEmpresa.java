package gui.controllers.empresa;

import dao.data.ApiError;
import dao.data.Empresa;
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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import servicios.EmpresaServicios;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class VerEmpresa implements Initializable {
    @FXML
    private Label lbId;
    @FXML
    private Label lbCif;
    @FXML
    private Label lbNombre;
    @FXML
    private Label lbDireccion;
    @FXML
    private Label lbFecha;
    @FXML
    private ListView<String> lvTrabajadores;
    @FXML
    private TextField tfBuscarEmpresaId;

    private PantallaPrincipal pantallaPrincipal;

    private final EmpresaServicios es;

    @Inject
    public VerEmpresa(EmpresaServicios es) {
        this.es = es;
    }

    public void setPrincipalPantalla(PantallaPrincipal pantallaPrincipal) {
        this.pantallaPrincipal = pantallaPrincipal;
    }

    public void buscarEmpresa() {
        try {
            int id = Integer.parseInt(tfBuscarEmpresaId.getText());
            var tarea = new Task<Either<ApiError, Empresa>>() {
                @Override
                protected Either<ApiError, Empresa> call() {
                    return es.getEmpresaId(id);
                }
            };
            tarea.setOnSucceeded(workerStateEvent -> {
                Try.of(() -> tarea.get().peek(this::pintarEmpresa)
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    private void pintarEmpresa(Empresa e) {
        lbId.setText(String.valueOf(e.getId()));
        lbCif.setText(e.getCif());
        lbDireccion.setText(e.getDireccion());
        lbNombre.setText(e.getNombre());
        lbFecha.setText(e.getFechaCreacion().toString());
        lvTrabajadores.getItems().clear();
        lvTrabajadores.getItems().addAll(e.getTrabajadores());
    }
}
