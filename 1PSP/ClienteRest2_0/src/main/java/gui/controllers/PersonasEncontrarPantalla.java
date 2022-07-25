package gui.controllers;


import io.vavr.control.Either;
import io.vavr.control.Try;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import modelos.Persona;
import servicios.PersonaServicios;
import utils.Constantes;

import javax.inject.Inject;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PersonasEncontrarPantalla implements Initializable {
    //FXML
    @FXML
    private ListView<Persona> lvListaPersonas;
    @FXML
    private ListView<String> lvNombres;
    @FXML
    private Label lbNombre;
    @FXML
    private ImageView ivImagen;
    @FXML
    private Hyperlink hlPagWeb;
    @FXML
    private Label lbBibliografia;
    @FXML
    private Label lbLugarNacimiento;
    @FXML
    private Label lbBirthday;
    @FXML
    private TextField tfIdPersona;

    private PrincipalPantalla principalPantalla;

    public void setPrincipalPantalla(PrincipalPantalla principalPantalla) {
        this.principalPantalla = principalPantalla;
    }

    //Inyectar
    private final PersonaServicios ps;

    @Inject
    public PersonasEncontrarPantalla(PersonaServicios ps) {
        this.ps = ps;
    }

    @FXML
    private void btnListPersona() {
        String nombrePersona = tfIdPersona.getText();
        if (!nombrePersona.isEmpty()) {
            var tarea = new Task<Either<String, List<Persona>>>() {

                @Override
                protected Either<String, List<Persona>> call() {
                    return ps.buscarPersonas(nombrePersona);
                }
            };
            tarea.setOnSucceeded(workerStateEvent -> {
                Try.of(() -> tarea.get().peek(this::mostrarListaPersonas)
                                .peekLeft(s -> principalPantalla.mandarAlert(Alert.AlertType.WARNING, s)))
                        .onFailure(t -> principalPantalla.mandarAlert(Alert.AlertType.ERROR, t.getMessage()));
                this.principalPantalla.getPrincipal().setCursor(Cursor.DEFAULT);
            });
            tarea.setOnFailed(workerStateEvent -> {
                principalPantalla.mandarAlert(Alert.AlertType.ERROR, workerStateEvent.getSource().getException().getMessage());
                this.principalPantalla.getPrincipal().setCursor(Cursor.DEFAULT);
            });
            new Thread(tarea).start();
            this.principalPantalla.getPrincipal().setCursor(Cursor.WAIT);
        } else {
            principalPantalla.mandarAlert(Alert.AlertType.ERROR, Constantes.NO_ESCRITO_NADA);
        }
    }

    @FXML
    private void buscarPersona() {
        if (lvListaPersonas.getSelectionModel().getSelectedItem() != null) {
            int id = lvListaPersonas.getSelectionModel().getSelectedItem().getId();
            var tarea = new Task<Either<String, Persona>>() {
                @Override
                protected Either<String, Persona> call() {
                    return ps.personaPorId(id);
                }
            };
            tarea.setOnSucceeded(workerStateEvent -> {
                Try.of(() -> tarea.get().peek(this::imprimirPersona)
                                .peekLeft(s -> principalPantalla.mandarAlert(Alert.AlertType.WARNING, s)))
                        .onFailure(t -> principalPantalla.mandarAlert(Alert.AlertType.ERROR, t.getMessage()));
                this.principalPantalla.getPrincipal().setCursor(Cursor.DEFAULT);
            });
            tarea.setOnFailed(workerStateEvent -> {
                principalPantalla.mandarAlert(Alert.AlertType.ERROR, workerStateEvent.getSource().getException().getMessage());
                this.principalPantalla.getPrincipal().setCursor(Cursor.DEFAULT);
            });
            new Thread(tarea).start();
            this.principalPantalla.getPrincipal().setCursor(Cursor.WAIT);
        } else {
            principalPantalla.mandarAlert(Alert.AlertType.WARNING, Constantes.NO_SELECCIONADO);
        }
    }

    @FXML
    private void btnLimpiar() {
        tfIdPersona.clear();
        lvListaPersonas.getItems().clear();
        limpiar();
    }

    private void mostrarListaPersonas(List<Persona> personas) {
        lvListaPersonas.getItems().clear();
        lvListaPersonas.getItems().addAll(personas);
    }

    private void imprimirPersona(Persona persona) {
        limpiar();
        lvNombres.getItems().addAll(persona.getAlsoKnownAs());
        lbNombre.setText(persona.getName());
        lbBibliografia.setText(persona.getBiography());
        lbLugarNacimiento.setText(persona.getPlaceOfBirth());
        lbBirthday.setText(String.valueOf(persona.getBirthday()));
        hlPagWeb.setText(persona.getHomepage());
        Image im = new Image(Constantes.URL_FOTOS + persona.getProfilePath());
        ivImagen.setImage(im);
    }

    private void limpiar() {
        lvNombres.getItems().clear();
        lbNombre.setText("");
        lbBibliografia.setText("");
        lbLugarNacimiento.setText("");
        lbBirthday.setText("");
        hlPagWeb.setText("");
        ivImagen.setImage(null);
    }

    @FXML
    private void irPagWeb() {
        //No sé agregar links
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //
    }
}
