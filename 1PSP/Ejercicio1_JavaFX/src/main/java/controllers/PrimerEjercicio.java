package controllers;

import dao.DaoPersona;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import modelo.Persona;
import servicios.persona.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class PrimerEjercicio implements Initializable {

    //Declaración de las variables tanto del fxml como de las necesarias.
    @FXML
    private TextField txfNombre;
    @FXML
    private TextField txfEdad;
    @FXML
    private RadioButton rbHombre;
    @FXML
    private RadioButton rbMujer;
    @FXML
    private DatePicker datePickFechaRegistro;
    @FXML
    private ToggleGroup sexo;
    @FXML
    private ListView<Persona> lvTodosNombres;
    @FXML
    private ListView<Persona> lvSelecionadosNombres;
    @FXML
    private ComboBox<String> cbSexo;

    private final iServiceAddPersona addPersona = new ServAddPersona();
    private final iServiceDeletePersona deletePersona = new ServDeletePersona();
    private final iServiceServUpdersona updPersona = new ServUpdPersona();
    private final iServiceConsultarPersona consultarPersona = new ServConsultarPersona();
    private Alert alerta;

    //Puse un comboBox para poder ver a todos a la par que empezar la app con varias personas, para no estar metiendo gente y viendo si desaparecen y tal.
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        alerta = new Alert(Alert.AlertType.ERROR);
        cbSexo.getItems().add("Mujer");
        cbSexo.getItems().add("Hombre");
        //cbSexo.getItems().add("VER TODO// AUXILIAR BORRAR LUEGO");
        cargarDatos();
    }

    //FUNCIONES

    //Cargo datos para comprobarlo todo mejor.
    private void cargarDatos() {
        Persona p1 = new Persona();
        Persona p2 = new Persona();
        Persona p3 = new Persona();
        Persona p4 = new Persona();
        Persona p5 = new Persona();
        Persona p6 = new Persona();
        p1.setEsMujer(true);
        p1.setEdad(78);
        p1.setNombre("Elena");
        p1.setFechaRegistro(LocalDate.of(1950, 12, 22));

        p2.setEsMujer(true);
        p2.setEdad(45);
        p2.setNombre("Martina");
        p2.setFechaRegistro(LocalDate.of(1999, 1, 2));

        p3.setEsMujer(false);
        p3.setEdad(25);
        p3.setNombre("Eduardo");
        p3.setFechaRegistro(LocalDate.of(2010, 11, 1));

        p4.setEsMujer(true);
        p4.setEdad(99);
        p4.setNombre("Eulalia");
        p4.setFechaRegistro(LocalDate.of(1925, 7, 15));

        p5.setEsMujer(false);
        p5.setEdad(57);
        p5.setNombre("Pedro");
        p5.setFechaRegistro(LocalDate.of(1998, 4, 12));

        p6.setEsMujer(false);
        p6.setEdad(18);
        p6.setNombre("Paquito");
        p6.setFechaRegistro(LocalDate.of(2019, 1, 2));

        addPersona.addPersona(p1);
        addPersona.addPersona(p2);
        addPersona.addPersona(p3);
        addPersona.addPersona(p4);
        addPersona.addPersona(p5);
        addPersona.addPersona(p6);
    }

    //Ya que recolectaré los datos dos veces (añadir y update), crearé una función para recolectarlos.
    private Persona recoleccionPersona() {
        Persona p = new Persona();
        p.setNombre(txfNombre.getText());
        p.setEdad(Integer.parseInt(txfEdad.getText()));
        p.setEsMujer(rbMujer.isSelected());
        p.setFechaRegistro(datePickFechaRegistro.getValue());
        return p;
    }

    //Haré una función para que se vayan borrando los datos marcados según se va usando y quede algo más bonito y vistoso.
    private void borrarDatos() {
        txfNombre.clear();
        txfEdad.clear();
        sexo.getSelectedToggle().setSelected(false);
        datePickFechaRegistro.setValue(null);
    }

    //Función de añadir una persona a la lista
    @FXML
    private void btnAnnadirPersona() {
        Persona p;
        try {
            p = recoleccionPersona();
            if (addPersona.addPersona(p)) {
                lvTodosNombres.getItems().add(p);
            } else {
                alerta.setContentText("La persona NO se ha añadido.");
                alerta.showAndWait();
            }
            borrarDatos();
        } catch (Exception e) {
            alerta.setContentText("Ha habido un error en el proceso. Inténtelo indicando bien los datos.");
            alerta.showAndWait();
        }
    }

    //Para que se vean seleccionados hombres o mujeres.
    @FXML
    private void cbBuscarPersona() {
        lvTodosNombres.getItems().clear();
        String queEs = cbSexo.getSelectionModel().getSelectedItem();
        /*Por qué lo hago en un for?? Pues porque para no estar enredando en dos atributos en DAO, si pregunto si es mujer y además, NO está en el LV de la izquierda, que se muestre.
        De esta forma solvento el que se dupliquen y luego me vaya dando error (vamos, que si paso un dato a la izquierda, no me vuelva a aparecer en la derecha).
        Estaba pensándolo hacer con dos lists de personas en el DAO, pero, veo como una danza de datos innecesaria, pudiendo modificar lo visible nada más.*/
        for (int i = 0; i < DaoPersona.getPersonas().size(); i++) {
            if (queEs.equals("Mujer")) {
                if (consultarPersona.consultarPersona(i).isEsMujer() && !lvSelecionadosNombres.getItems().contains(consultarPersona.consultarPersona(i))) {
                    lvTodosNombres.getItems().add(consultarPersona.consultarPersona(i));
                }
            } else if (queEs.equals("Hombre")) {
                if (!consultarPersona.consultarPersona(i).isEsMujer() && !lvSelecionadosNombres.getItems().contains(consultarPersona.consultarPersona(i))) {
                    lvTodosNombres.getItems().add(consultarPersona.consultarPersona(i));
                }
            }
            /* else{
                if(! lvSelecionadosNombres.getItems().contains(personas.getPersonas().get(i)))
                    lvTodosNombres.getItems().add(personas.getPersonas().get(i));
            }*/
        }
    }


    //Manda a una persona al LV de peligro, donde poder modificar sus datos o borrarlo
    @FXML
    private void btSelPersModificarla() {
        if (lvTodosNombres.getSelectionModel().getSelectedItem() != null) {
            lvSelecionadosNombres.getItems().add(lvTodosNombres.getSelectionModel().getSelectedItem());
            lvTodosNombres.getItems().remove(lvTodosNombres.getSelectionModel().getSelectedItem());
            lvTodosNombres.getSelectionModel().clearSelection();
        } else {
            alerta.setContentText("No has seleccionado a ninguna persona.");
            alerta.showAndWait();
        }
    }

    //Lo devuelve al LV de la izquierda, donde no correrá peligro ninguno D:.
    @FXML
    private void btDevolverPersona() {
        if (lvSelecionadosNombres.getSelectionModel().getSelectedItem() != null) {
            lvTodosNombres.getItems().add(lvSelecionadosNombres.getSelectionModel().getSelectedItem());
            lvSelecionadosNombres.getItems().remove(lvSelecionadosNombres.getSelectionModel().getSelectedItem());
            lvSelecionadosNombres.getSelectionModel().clearSelection();
            borrarDatos();
        } else {
            alerta.setContentText("No has seleccionado a ninguna persona.");
            alerta.showAndWait();
        }
    }

    //Aquí lo que haremos será borrar al seleccionado, tanto del LV como del dao.
    @FXML
    private void btBorrarSelec() {
        if (lvSelecionadosNombres.getSelectionModel().getSelectedItem() != null) {
            Persona p = lvSelecionadosNombres.getSelectionModel().getSelectedItem();
            lvSelecionadosNombres.getItems().remove(p);
            deletePersona.deletePersona(p);
            lvSelecionadosNombres.getSelectionModel().clearSelection();
            borrarDatos();
        } else {
            alerta.setContentText("No has seleccionado a ninguna persona, así pues no se podrá borrar nada.");
            alerta.showAndWait();
        }
    }

    //Cuando se clicka en el LV de la izquierda, quiero que se vuelquen los datos en las celdas de información.
    @FXML
    private void ponerDatos() {
        if (lvSelecionadosNombres.getSelectionModel().getSelectedItem() != null) {
            Persona p = lvSelecionadosNombres.getSelectionModel().getSelectedItem();
            txfNombre.setText(p.getNombre());
            txfEdad.setText(String.valueOf(p.getEdad()));
            datePickFechaRegistro.setValue(p.getFechaRegistro());
            if (p.isEsMujer()) {
                rbMujer.setSelected(true);
            } else {
                rbHombre.setSelected(true);
            }
        }
    }

    //Para poder modificar los datos una vez están seleccionados.
    @FXML
    private void cambiarDatos() {
        if (lvSelecionadosNombres.getSelectionModel().getSelectedItem() != null) {
            Persona p;
            try {
                p = recoleccionPersona();
                if (updPersona.modificarDatosPersona(lvSelecionadosNombres.getSelectionModel().getSelectedItem(), p)) {
                    lvSelecionadosNombres.getItems().set(lvSelecionadosNombres.getSelectionModel().getSelectedIndex(), p);
                } else {
                    alerta.setContentText("ERROR. No se ha podido cambiar los datos.");
                    alerta.showAndWait();
                }
                lvSelecionadosNombres.getSelectionModel().clearSelection();
                borrarDatos();
            } catch (Exception e) {
                alerta.setContentText("ERROR. Los datos son incorrectos, asegurese de que se introducen correctamente.");
                alerta.showAndWait();
            }
        } else {
            alerta.setContentText("ERROR. No hay ninguna persona seleccionada");
            alerta.showAndWait();
        }
    }
}
