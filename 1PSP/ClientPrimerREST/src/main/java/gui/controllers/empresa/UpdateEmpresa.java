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

public class UpdateEmpresa implements Initializable {
    @FXML
    private TextField tfCif;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfDireccion;
    @FXML
    private DatePicker dpFecha;
    @FXML
    private TextField tfIdEmpresa;

    private PantallaPrincipal pantallaPrincipal;

    //Para ayudarme.
    private Empresa empresaBuscada = null;

    private final EmpresaServicios es;

    @Inject
    public UpdateEmpresa(EmpresaServicios es) {
        this.es = es;
    }

    public void setPrincipalPantalla(PantallaPrincipal pantallaPrincipal) {
        this.pantallaPrincipal = pantallaPrincipal;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void buscarEmpresa() {
        try {
            int id = Integer.parseInt(tfIdEmpresa.getText());
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

    @FXML
    private void updateEmpresa() {
        if (empresaBuscada != null) {
            Empresa e = recogerDatos();
            var tarea = new Task<Either<ApiError, Empresa>>() {
                @Override
                protected Either<ApiError, Empresa> call() {
                    return es.updateEmpresa(e);
                }
            };
            tarea.setOnSucceeded(workerStateEvent -> {
                Try.of(() -> tarea.get().peek(s -> pantallaPrincipal.mandarAlert(Alert.AlertType.INFORMATION, Constantes.LA_EMPRESA_HA_SIDO_MODIFICADA_CON_EXITO))
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
            borrar();
        } else {
            pantallaPrincipal.mandarAlert(Alert.AlertType.ERROR, Constantes.BUSCA_ANTES_UNA_EMPRESA);
        }

    }

    private void pintarEmpresa(Empresa e) {
        this.empresaBuscada = e;
        tfCif.setText(e.getCif());
        tfNombre.setText(e.getNombre());
        tfDireccion.setText(e.getDireccion());
        dpFecha.setValue(e.getFechaCreacion());
    }

    private void borrar() {
        this.empresaBuscada = null;
        tfCif.setText("");
        tfNombre.setText("");
        tfDireccion.setText("");
        dpFecha.setValue(null);
        tfIdEmpresa.setText("");
    }

    private Empresa recogerDatos() {
        return new Empresa(tfCif.getText(), empresaBuscada.getTrabajadores(), tfDireccion.getText(), dpFecha.getValue(), empresaBuscada.getId(), tfNombre.getText());
    }
}
