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
import javafx.scene.control.ListView;
import modelo.Sexo;
import servicios.PersonasServicios;

import javax.inject.Inject;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Casamientos implements Initializable {
    @FXML
    private ListView<PersonaData> lvMujeres;
    @FXML
    private ListView<PersonaData> lvHombres;

    private Principal principal;
    private final PersonasServicios ps;

    @Inject
    public Casamientos(PersonasServicios ps) {
        this.ps = ps;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void seCasan() {
        if (lvHombres.getSelectionModel().getSelectedItem() != null && lvMujeres.getSelectionModel().getSelectedItem() != null) {
            var tarea = new Task<Either<String, Boolean>>() {
                @Override
                protected Either<String, Boolean> call() {
                    return ps.realizarEmparejamiento(lvHombres.getSelectionModel().getSelectedItem().getId(),
                            lvMujeres.getSelectionModel().getSelectedItem().getId());
                }
            };
            tarea.setOnSucceeded(workerStateEvent -> {
                Try.of(() -> tarea.get().peek(p -> avisarCasamiento(lvHombres.getSelectionModel().getSelectedItem(),
                                        lvMujeres.getSelectionModel().getSelectedItem()))
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
        } else {
            principal.mandarAlert(Alert.AlertType.WARNING,
                    Constantes.NO_HAS_SELECCIONADO_A_UN_HOMBRE_Y_UNA_MUJER_AQUI_SOMOS_ULTRACONSERVADORES_NO_HAY_VICIO);
        }
    }

    public void cargarCasamientos() {
        cargarMujeres();
        cargarHombres();

    }

    private void cargarMujeres() {
        var tarea = new Task<Either<String, List<PersonaData>>>() {
            @Override
            protected Either<String, List<PersonaData>> call() {
                return ps.getAllPersonasSexo(Sexo.Mujer.name());
            }
        };
        tarea.setOnSucceeded(workerStateEvent -> {
            Try.of(() -> tarea.get().peek(this::listMujeres)
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

    private void cargarHombres() {
        var tarea = new Task<Either<String, List<PersonaData>>>() {
            @Override
            protected Either<String, List<PersonaData>> call() {
                return ps.getAllPersonasSexo(Sexo.Hombre.name());
            }
        };
        tarea.setOnSucceeded(workerStateEvent -> {
            Try.of(() -> tarea.get().peek(this::listHombres)
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

    private void listMujeres(List<PersonaData> personas) {
        lvMujeres.getItems().clear();
        lvMujeres.getItems().addAll(personas);
    }

    private void listHombres(List<PersonaData> personas) {
        lvHombres.getItems().clear();
        lvHombres.getItems().addAll(personas);
    }

    private void avisarCasamiento(PersonaData hombre, PersonaData mujer) {
        principal.mandarAlert(Alert.AlertType.INFORMATION, Constantes.YA_SE_HAN_CASADO);
        lvHombres.getItems().remove(hombre);
        lvMujeres.getItems().remove(mujer);
    }
}
