package controllers;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import modelo.Persona;
import servicios.persona.GetPersonas;
import servicios.persona.VerPersonas;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewPantalla implements Initializable {

    @FXML
    private ComboBox<String> cbSelecSexo;
    @FXML
    private ListView<Persona> lvVerPersonas;

    private final VerPersonas verPersonas = new VerPersonas();
    private final GetPersonas getPersonas = new GetPersonas();

    public void volcarInfo() {
        lvVerPersonas.getItems().clear();
        lvVerPersonas.getItems().addAll(getPersonas.getPersonas());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbSelecSexo.getItems().add("Hombre");
        cbSelecSexo.getItems().add("Mujer");
        cbSelecSexo.getItems().add("Ver todo");
    }

    @FXML
    private void cbBuscarPersona() {
        if (cbSelecSexo.getSelectionModel().getSelectedItem().contains("Ver todo")) {
            volcarInfo();
        } else if (cbSelecSexo.getSelectionModel().getSelectedItem().contains("Mujer")) {
            lvVerPersonas.getItems().clear();
            lvVerPersonas.getItems().addAll(verPersonas.verPersonas(true));
        } else {
            lvVerPersonas.getItems().clear();
            lvVerPersonas.getItems().addAll(verPersonas.verPersonas(false));
        }
    }
}
