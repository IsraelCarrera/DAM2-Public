package gui.controllers;

import dao.data.PersonaData;
import gui.utils.Constantes;
import io.vavr.control.Either;
import io.vavr.control.Try;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import modelo.EstadoCivil;
import servicios.PersonasServicios;

import javax.inject.Inject;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Consultas implements Initializable {
    @FXML
    private ComboBox<String> cbEstadoCivil;
    @FXML
    private TextField tfProcedencia;
    @FXML
    private TextField tfNacimiento;
    @FXML
    private TextField tfHijos;
    @FXML
    private ListView<PersonaData> lvPersonas;

    private Principal principal;

    private final PersonasServicios ps;

    @Inject
    public Consultas(PersonasServicios ps) {
        this.ps = ps;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }


    @FXML
    private void buscarPersonas() {
        try {
            int anno;
            int nHijos;
            String estadoCivil;
            if (!tfNacimiento.getText().isEmpty()) {
                anno = Integer.parseInt(tfNacimiento.getText());
            } else {
                anno = -1;
            }
            if (!tfHijos.getText().isEmpty()) {
                nHijos = Integer.parseInt(tfHijos.getText());
            } else {
                nHijos = -1;
            }
            if (cbEstadoCivil.getSelectionModel().getSelectedItem() != null) {
                estadoCivil = cbEstadoCivil.getSelectionModel().getSelectedItem();
            } else {
                estadoCivil = "";
            }
            String procedencia = tfProcedencia.getText();
            var tarea = new Task<Either<String, List<PersonaData>>>() {
                @Override
                protected Either<String, List<PersonaData>> call() {
                    return ps.getPorFiltros(procedencia, anno, nHijos, estadoCivil);
                }
            };
            tarea.setOnSucceeded(workerStateEvent -> {
                Try.of(() -> tarea.get().peek(this::meterPersonasLista)
                                .peekLeft(s -> principal.mandarAlert(Alert.AlertType.WARNING, s)))
                        .onFailure(t -> principal.mandarAlert(Alert.AlertType.ERROR, t.getMessage()));
                this.principal.getPrincipal().setCursor(Cursor.DEFAULT);
            });
            tarea.setOnFailed(workerStateEvent -> {
                principal.mandarAlert(Alert.AlertType.ERROR, workerStateEvent.getSource().getException().getMessage());
                principal.getPrincipal().setCursor(Cursor.DEFAULT);
            });
            new Thread(tarea).start();
            this.principal.getPrincipal().setCursor(Cursor.WAIT);
        } catch (Exception e) {
            principal.mandarAlert(Alert.AlertType.ERROR, Constantes.NO_HAS_INTRODUCIDO_UN_NUMERO_EN_EL_CAMPO_ANO_O_NUMERO_DE_HIJOS);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (EstadoCivil ec : EstadoCivil.values()) {
            cbEstadoCivil.getItems().addAll(ec.name());
        }
    }

    private void meterPersonasLista(List<PersonaData> personas) {
        lvPersonas.getItems().clear();
        lvPersonas.getItems().addAll(personas);
    }
}
