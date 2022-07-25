package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import modelo.Persona;
import servicios.persona.GetPersonas;
import servicios.persona.UpdatePersona;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class UpdatePantalla implements Initializable {

    @FXML
    private ToggleGroup sexo;
    @FXML
    private ListView<Persona> lvLista;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfEdad;
    @FXML
    private DatePicker dpFecha;
    @FXML
    private RadioButton mujer;
    @FXML
    private RadioButton hombre;


    private final UpdatePersona updPersona = new UpdatePersona();
    private final GetPersonas getPersonas = new GetPersonas();
    Alert alerta = new Alert(Alert.AlertType.WARNING);


    public void volcarInfo() {
        lvLista.getItems().clear();
        lvLista.getItems().addAll(getPersonas.getPersonas());
    }

    //Reutilizo el codigo de add persona para recolectar los datos del update
    private Persona recoleccionPersona() {
        Persona p = new Persona();
        if (tfNombre.getText().isEmpty() || tfEdad.getText().isEmpty()) {
            return null;
        } else {
            p.setNombre(tfNombre.getText());
            p.setEdad(Integer.parseInt(tfEdad.getText()));
            LocalDate fecha = dpFecha.getValue();
            p.setFechaRegistro(fecha);
            if (mujer.isSelected() || hombre.isSelected()) {
                p.setEsMujer(mujer.isSelected());
                return p;
            } else {
                return null;
            }
        }
    }

    //Reutilizo la de limpiar, la hago una función porque la utilizare tmbn en el botón.
    private void clear() {
        tfNombre.clear();
        tfEdad.clear();
        if (mujer.isSelected() || hombre.isSelected()) {
            sexo.getSelectedToggle().setSelected(false);
        }
        dpFecha.setValue(null);
    }

    @FXML
    private void btnUpdatePersona() {
        if (lvLista.getSelectionModel().getSelectedItem() != null) {
            Persona p;
            try {
                p = recoleccionPersona();
                if (updPersona.updatePersona(lvLista.getSelectionModel().getSelectedItem(), p)) {
                    lvLista.getItems().set(lvLista.getSelectionModel().getSelectedIndex(), p);
                } else {
                    alerta.setContentText("ERROR. No se ha podido cambiar los datos.");
                    alerta.showAndWait();
                }
                lvLista.getSelectionModel().clearSelection();
                clear();
            } catch (Exception e) {
                alerta.setContentText("ERROR. Los datos son incorrectos, asegurese de que se introducen correctamente.");
                alerta.showAndWait();
            }
        } else {
            alerta.setContentText("No ha seleccionado a ninguna persona.");
            alerta.showAndWait();
        }
    }

    //Copio la misma función que en addpersona.
    @FXML
    private void limpiarTexto() {
        lvLista.getSelectionModel().clearSelection();
        clear();
    }

    @FXML
    private void mostrarDatos() {
        if (lvLista.getSelectionModel().getSelectedItem() != null) {
            Persona p = lvLista.getSelectionModel().getSelectedItem();
            tfNombre.setText(p.getNombre());
            tfEdad.setText(String.valueOf(p.getEdad()));
            dpFecha.setValue(p.getFechaRegistro());
            if (p.isEsMujer()) {
                mujer.setSelected(true);
            } else {
                hombre.setSelected(true);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }


}
