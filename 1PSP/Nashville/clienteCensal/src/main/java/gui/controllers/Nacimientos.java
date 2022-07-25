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
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class Nacimientos implements Initializable {
    @FXML
    private ListView<PersonaData> lvPadre;
    @FXML
    private ListView<PersonaData> lvMadre;
    @FXML
    private DatePicker dpFechaNaci;
    @FXML
    private ComboBox<String> cbSexo;
    @FXML
    private TextField tfNombre;

    private Principal principal;

    private final PersonasServicios ps;

    @Inject
    public Nacimientos(PersonasServicios ps) {
        this.ps = ps;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }

    @FXML
    private void addBebe() {
        if (lvMadre.getSelectionModel().getSelectedItem() != null && lvPadre.getSelectionModel().getSelectedItem() != null) {
            PersonaData bebe = construirBebe();
            var tarea = new Task<Either<Integer, PersonaData>>() {
                @Override
                protected Either<Integer, PersonaData> call() {
                    return ps.addBebe(lvPadre.getSelectionModel().getSelectedItem().getId(),
                            lvMadre.getSelectionModel().getSelectedItem().getId(), bebe);
                }
            };
            tarea.setOnSucceeded(workerStateEvent -> {
                Try.of(() -> tarea.get().peek(this::bebeAddExito)
                                .peekLeft(n -> bebeProblemon(n, lvPadre.getSelectionModel().getSelectedItem().getId(),
                                        lvMadre.getSelectionModel().getSelectedItem().getId())))
                        .onFailure(t -> principal.mandarAlert(Alert.AlertType.ERROR, t.getMessage()));
                this.principal.getPrincipal().setCursor(Cursor.DEFAULT);
            });
            tarea.setOnFailed(workerStateEvent -> {
                principal.mandarAlert(Alert.AlertType.ERROR, workerStateEvent.getSource().getException().getMessage());
                principal.getPrincipal().setCursor(Cursor.DEFAULT);
            });
            new Thread(tarea).start();
            this.principal.getPrincipal().setCursor(Cursor.WAIT);
        } else {
            principal.mandarAlert(Alert.AlertType.WARNING, Constantes.LOS_BEBES_NO_VIENEN_POR_CIGUENA_SELECCIONA_A_LOS_PADRES);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbSexo.getItems().add(Sexo.Hombre.name());
        cbSexo.getItems().add(Sexo.Mujer.name());
        cbSexo.setValue(Sexo.Mujer.name());
    }

    public void cargarNacimientos() {
        cargarPersonas();
    }

    private void cargarPersonas() {
        var tarea = new Task<Either<String, List<PersonaData>>>() {
            @Override
            protected Either<String, List<PersonaData>> call() {
                return ps.getAllPersonas();
            }
        };
        tarea.setOnSucceeded(workerStateEvent -> {
            Try.of(() -> tarea.get().peek(this::listPersonas)
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


    private void listPersonas(List<PersonaData> personas) {
        List<PersonaData> hombres = personas.stream().filter(p -> p.getSexo().equals(Sexo.Hombre.name())).collect(Collectors.toList());
        List<PersonaData> mujeres = personas.stream().filter(p -> p.getSexo().equals(Sexo.Mujer.name())).collect(Collectors.toList());
        lvPadre.getItems().clear();
        lvPadre.getItems().addAll(hombres);
        lvMadre.getItems().clear();
        lvMadre.getItems().addAll(mujeres);
    }

    private PersonaData construirBebe() {
        PersonaData p = new PersonaData();
        p.setFechaNacimiento(dpFechaNaci.getValue());
        p.setSexo(cbSexo.getValue());
        p.setNombre(tfNombre.getText());
        p.setLugarProcedencia(Constantes.NASHVILLE);
        return p;
    }

    private void bebeAddExito(PersonaData p) {
        principal.mandarAlert(Alert.AlertType.INFORMATION, "El bebe se ha registrado con éxito.");
        if (p.getSexo().equals(Sexo.Hombre.name())) {
            lvPadre.getItems().add(p);
        } else {
            lvMadre.getItems().add(p);
        }
    }

    private void bebeProblemon(int n, String idP1, String idP2) {
        if (n == 499) {
            //Creo la alerta aquí porque necesito la variable de alerta.
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setContentText(Constantes.LAS_DOS_PERSONAS_QUE_HAN_TENIDO_UN_HIJO_NO_ESTAN_CASADOS_ESTAS_SEGURO_SI_LE_DAS_A_QUE_SI_SE_LES_MANDARA_AL_EXILIO);
            alerta.showAndWait();
            if (alerta.getResult() == ButtonType.OK) {
                mandarExilio(idP1, idP2);
            } else {
                principal.mandarAlert(Alert.AlertType.INFORMATION,
                        Constantes.TE_HAS_EQUIVOCADO_FIJATE_MEJOR_NO_AGREGAMOS_AL_BEBE_NI_LOS_MANDAMOS_AL_EXILIO);
            }
        } else if (n == 488) {
            principal.mandarAlert(Alert.AlertType.ERROR, Constantes.HA_OCURRIDO_UN_ERROR_AL_REGISTRAR_EL_BEBE);
        } else {
            principal.mandarAlert(Alert.AlertType.ERROR,
                    Constantes.ERROR_EN_EL_SERVIDOR_ESTA_DESCONECTADO_PRUEBE_MAS_TARDE);
        }
    }

    private void mandarExilio(String idP1, String idP2) {
        var tarea = new Task<Either<String, Boolean>>() {
            @Override
            protected Either<String, Boolean> call() {
                return ps.promiscuosFuera(idP1, idP2);
            }
        };
        tarea.setOnSucceeded(workerStateEvent -> {
            Try.of(() -> tarea.get().peek(p -> principal.mandarAlert(Alert.AlertType.INFORMATION,
                                    Constantes.YA_SE_LES_HA_ECHADO_DEL_PUEBLO))
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
}
