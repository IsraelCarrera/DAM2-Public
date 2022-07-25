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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import servicios.EmpresaServicios;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class AddEmpresa implements Initializable {
    @FXML
    private TextField tfCif;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfDireccion;
    @FXML
    private DatePicker dpFecha;

    private PantallaPrincipal pantallaPrincipal;

    private final EmpresaServicios es;

    @Inject
    public AddEmpresa(EmpresaServicios es) {
        this.es = es;
    }

    public void setPrincipalPantalla(PantallaPrincipal pantallaPrincipal) {
        this.pantallaPrincipal = pantallaPrincipal;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void addEmpresa() {
        Empresa e = recolectarDatos();
        var tarea = new Task<Either<ApiError, Empresa>>() {
            @Override
            protected Either<ApiError, Empresa> call() {
                return es.addEmpresa(e);
            }
        };
        tarea.setOnSucceeded(workerStateEvent -> {
            Try.of(() -> tarea.get().peek(p -> this.pantallaPrincipal.mandarAlert(Alert.AlertType.INFORMATION, Constantes.EMPRESA_AGREGADA_CON_EXITO))
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

    private Empresa recolectarDatos() {
        Empresa e = new Empresa(tfCif.getText(), tfNombre.getText(), tfDireccion.getText(), dpFecha.getValue());
        return e;
    }
}
