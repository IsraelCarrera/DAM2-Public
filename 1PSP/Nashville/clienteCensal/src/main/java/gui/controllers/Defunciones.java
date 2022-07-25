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
import servicios.PersonasServicios;

import javax.inject.Inject;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Defunciones implements Initializable {
    @FXML
    private ListView<PersonaData> lvPersonas;

    private Principal principal;

    private final PersonasServicios ps;

    @Inject
    public Defunciones(PersonasServicios ps) {
        this.ps = ps;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }

    @FXML
    private void matarPersona() {
        if (lvPersonas.getSelectionModel().getSelectedItem() != null) {
            var tarea = new Task<Either<Integer, Integer>>() {
                @Override
                protected Either<Integer, Integer> call() {
                    return ps.matarPersona(lvPersonas.getSelectionModel().getSelectedItem().getId());
                }
            };
            tarea.setOnSucceeded(workerStateEvent -> {
                Try.of(() -> tarea.get().peek(p -> personaMuertaExito(lvPersonas.getSelectionModel().getSelectedItem()))
                                .peekLeft(this::noMuerta))
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
                    Constantes.NO_HAS_SELECCIONADO_A_UNA_PERSONA_DE_MOMENTO_NO_SE_PUEDE_MATAR_A_LO_INVISIBLE);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void cargarDefunciones() {
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

    private void personaMuertaExito(PersonaData p) {
        principal.mandarAlert(Alert.AlertType.INFORMATION, Constantes.LA_PERSONA_HA_SIDO_ANIQUILADA_CON_EXITO);
        lvPersonas.getItems().remove(p);

    }

    private void noMuerta(int n) {
        if (n == 489) {
            principal.mandarAlert(Alert.AlertType.WARNING,
                    Constantes.HEMOS_ANIQUILADO_A_ESTA_PERSONA_PERO_HA_RESUCITADO_ES_HIJO_DE_ALGUIEN_EXTERMINA_A_SUS_PROGENITORES_ANTES);
        } else {
            principal.mandarAlert(Alert.AlertType.ERROR, Constantes.ERROR_AL_ELIMINAR_A_UNA_PERSONA);
        }
    }
}
