package controllers;

import dao.modelos.Persona;
import dao.modelos.PersonaSacadaDeBuscar;
import io.vavr.control.Either;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import servicios.PersonaServicios;
import utils.Constantes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PersonasEncontrarPantalla implements Initializable {
    @FXML
    private ListView<PersonaSacadaDeBuscar> lvListaPersonas;
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

    @FXML
    private void btnListPersona() {
        Alert alerta;
        PersonaServicios ps = new PersonaServicios();
        String nombrePersona = tfIdPersona.getText();
        if (!nombrePersona.isEmpty()) {
            Either<String, List<PersonaSacadaDeBuscar>> personas = ps.buscarPersonas(nombrePersona);
            if (personas.isRight()) {
                if(!personas.get().isEmpty()) {
                    lvListaPersonas.getItems().clear();
                    lvListaPersonas.getItems().addAll(personas.get());
                }else{
                    alerta = new Alert(Alert.AlertType.WARNING);
                    alerta.setContentText(Constantes.NO_PERSONA_EXISTE);
                    alerta.showAndWait();
                }
            } else {
                alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setContentText(personas.getLeft());
                alerta.showAndWait();
            }
        } else {
            alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText(Constantes.NO_ESCRITO_NADA);
            alerta.showAndWait();
        }
    }

    @FXML
    private void buscarPersona() {
        Alert alerta;
        PersonaServicios ps = new PersonaServicios();
        if (lvListaPersonas.getSelectionModel().getSelectedItem() != null) {
            int id = lvListaPersonas.getSelectionModel().getSelectedItem().getId();
            Either<String,Persona> persona = ps.personaPorId(id);
            if(persona.isRight()) {
                limpiar();
                lvNombres.getItems().addAll(persona.get().getAlsoKnownAs());
                lbNombre.setText(persona.get().getName());
                lbBibliografia.setText(persona.get().getBiography());
                lbLugarNacimiento.setText(persona.get().getPlaceOfBirth());
                lbBirthday.setText(String.valueOf(persona.get().getBirthday()));
                hlPagWeb.setText(persona.get().getHomepage());
                Image im = new Image(Constantes.URL_FOTOS + persona.get().getProfilePath());
                ivImagen.setImage(im);
            }else{
                alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setContentText(persona.getLeft());
                alerta.showAndWait();
            }
        } else {
            alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setContentText(Constantes.NO_SELECCIONADO);
            alerta.showAndWait();
        }
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
    private void btnLimpiar() {
        tfIdPersona.clear();
        lvListaPersonas.getItems().clear();
        limpiar();
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
