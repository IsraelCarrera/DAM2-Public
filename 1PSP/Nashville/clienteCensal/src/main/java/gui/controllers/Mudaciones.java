package gui.controllers;

import dao.data.PersonaData;
import gui.utils.Constantes;
import io.vavr.control.Either;
import io.vavr.control.Try;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import modelo.Sexo;
import servicios.PersonasServicios;

import javax.inject.Inject;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class Mudaciones implements Initializable {
    @FXML
    private ListView<PersonaData> lvPersonas;
    @FXML
    private ComboBox<String> cbSexo;
    @FXML
    private DatePicker dpFechaNaci;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfLugarProcedencia;

    private Principal principal;

    private final PersonasServicios ps;

    @Inject
    public Mudaciones(PersonasServicios ps) {
        this.ps = ps;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }

    @FXML
    private void imprimirPersona() {
        if (lvPersonas.getSelectionModel().getSelectedItem() != null) {
            dpFechaNaci.setValue(lvPersonas.getSelectionModel().getSelectedItem().getFechaNacimiento());
            tfNombre.setText(lvPersonas.getSelectionModel().getSelectedItem().getNombre());
            tfLugarProcedencia.setText(lvPersonas.getSelectionModel().getSelectedItem().getLugarProcedencia());
            cbSexo.setValue(lvPersonas.getSelectionModel().getSelectedItem().getSexo());
        }
    }

    @FXML
    private void addPersona() {
        PersonaData p = recolectarDatos();
        var tarea = new Task<Either<String, PersonaData>>() {
            @Override
            protected Either<String, PersonaData> call() {
                return ps.vieneUnaPersona(p);
            }
        };
        tarea.setOnSucceeded(workerStateEvent -> {
            Try.of(() -> tarea.get().peek(this::vienenAMudarse)
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
        limpiar();
    }

    @FXML
    private void updatePersona() {
        if (lvPersonas.getSelectionModel().getSelectedItem() != null) {
            int index = lvPersonas.getSelectionModel().getSelectedIndex();
            PersonaData p = lvPersonas.getSelectionModel().getSelectedItem();
            p.setSexo(cbSexo.getValue());
            p.setFechaNacimiento(dpFechaNaci.getValue());
            p.setNombre(tfNombre.getText());
            p.setLugarProcedencia(tfLugarProcedencia.getText());
            var tarea = new Task<Either<String, PersonaData>>() {
                @Override
                protected Either<String, PersonaData> call() {
                    return ps.updatePersona(p);
                }
            };
            tarea.setOnSucceeded(workerStateEvent -> {
                Try.of(() -> tarea.get().peek(pr -> this.modificandoPersona(pr, index))
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
            limpiar();
        }
    }

    @FXML
    private void deletePersona() {
        if (lvPersonas.getSelectionModel().getSelectedItem() != null) {
            String idPersona = lvPersonas.getSelectionModel().getSelectedItem().getId();
            var tarea = new Task<Either<String, List<PersonaData>>>() {
                @Override
                protected Either<String, List<PersonaData>> call() {
                    return ps.sevaUnaPersona(idPersona);
                }
            };
            tarea.setOnSucceeded(workerStateEvent -> {
                Try.of(() -> tarea.get().peek(this::seVanDelPais)
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
            limpiar();
        }
        if (lvPersonas.getSelectionModel().getSelectedItem() != null) {
            limpiar();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbSexo.getItems().add(Sexo.Hombre.name());
        cbSexo.getItems().add(Sexo.Mujer.name());
        cbSexo.setValue(Sexo.Mujer.name());
    }

    public void cargarMudaciones() {
        var tarea = new Task<Either<String, List<PersonaData>>>() {
            @Override
            protected Either<String, List<PersonaData>> call() {
                return ps.getAllPersonas();
            }
        };
        tarea.setOnSucceeded(workerStateEvent -> {
            Try.of(() -> tarea.get().peek(this::meterDatos)
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
    }

    private void meterDatos(List<PersonaData> personas) {
        lvPersonas.getItems().clear();
        lvPersonas.getItems().addAll(personas);
    }

    private PersonaData recolectarDatos() {
        LocalDate naci = dpFechaNaci.getValue();
        String nombre = tfNombre.getText();
        String procedencia = tfLugarProcedencia.getText();
        String sexo = cbSexo.getValue();
        PersonaData p = new PersonaData();
        p.setSexo(sexo);
        p.setFechaNacimiento(naci);
        p.setNombre(nombre);
        p.setLugarProcedencia(procedencia);
        return p;
    }

    private void vienenAMudarse(PersonaData p) {
        lvPersonas.getItems().add(p);
        this.principal.mandarAlert(Alert.AlertType.INFORMATION, Constantes.ESTA_PERSONA_SE_HA_REGISTRADO_CON_EXITO);
    }

    private void seVanDelPais(List<PersonaData> personas) {
        lvPersonas.getItems().removeAll(personas);
        principal.mandarAlert(Alert.AlertType.INFORMATION, Constantes.PERSONAS_QUE_SE_VAN + personas.size());
    }

    private void modificandoPersona(PersonaData p, int index) {
        lvPersonas.getItems().set(index, p);
    }

    private void limpiar() {
        lvPersonas.getSelectionModel().clearSelection();
        dpFechaNaci.setValue(null);
        tfNombre.setText("");
        tfLugarProcedencia.setText("");
        cbSexo.setValue(null);
    }
}
