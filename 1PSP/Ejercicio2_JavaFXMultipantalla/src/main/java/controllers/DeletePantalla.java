package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import modelo.Persona;
import servicios.persona.DeletePersona;
import servicios.persona.GetPersonas;

import java.net.URL;
import java.util.ResourceBundle;

public class DeletePantalla implements Initializable {

    @FXML
    private ListView<Persona> lvLista;

    private final GetPersonas getPersonas = new GetPersonas();
    private final DeletePersona delpersona = new DeletePersona();
    Alert alerta = new Alert(Alert.AlertType.INFORMATION);

    public void volcarInfo() {
        lvLista.getItems().clear();
        lvLista.getItems().addAll(getPersonas.getPersonas());
    }

    @FXML
    private void btDeletePersona() {
        if (lvLista.getSelectionModel().getSelectedItem() != null) {
            Persona p = lvLista.getSelectionModel().getSelectedItem();
            lvLista.getItems().remove(p);
            delpersona.deletePersona(p);
            lvLista.getSelectionModel().clearSelection();
        } else {
            alerta.setContentText("No has seleccionado a niguna persona.");
            alerta.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
