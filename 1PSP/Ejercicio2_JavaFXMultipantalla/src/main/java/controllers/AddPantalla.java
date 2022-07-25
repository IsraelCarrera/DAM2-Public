package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import modelo.Persona;
import servicios.persona.AddPersona;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddPantalla implements Initializable {

    @FXML
    private ToggleGroup sexo;
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

    private final AddPersona add = new AddPersona();
    private final Alert alerta = new Alert(Alert.AlertType.ERROR);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    //Creo una función para recolectar persona. Intentando que si introduce algún dato vacio y/o nullo, retorne una persona "Null"
    //Así, en la creación de persona en servicios, si es null, no se creará y retornará un false en addpersona.
    //Igualmente, no sé como controlar la variable LocalDate, es la única que no puedo controlar.
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

    private void clear() {
        tfNombre.clear();
        tfEdad.clear();
        if (mujer.isSelected() || hombre.isSelected()) {
            sexo.getSelectedToggle().setSelected(false);
        }
        dpFecha.setValue(null);
    }


    @FXML
    private void addPersona() {
        Persona p;
        try {
            p = recoleccionPersona();
            if (add.addPersona(p)) {
                clear();
            } else {
                alerta.setContentText("No se ha añadido la persona, fallo en la fecha de registro, sexo o nombre.");
                alerta.showAndWait();
            }
        } catch (Exception e) {
            alerta.setContentText("No se ha añadido la persona, fallo en la edad.");
            alerta.showAndWait();
        }
    }

    @FXML
    private void limpiarTexto() {
        clear();
    }
}
