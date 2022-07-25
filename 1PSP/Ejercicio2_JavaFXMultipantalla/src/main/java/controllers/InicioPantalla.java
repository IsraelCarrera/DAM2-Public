package controllers;

import javafx.fxml.Initializable;
import modelo.Persona;
import servicios.persona.AddPersona;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class InicioPantalla implements Initializable {
    AddPersona addPersona = new AddPersona();

    //Voy a cargar datos para que haya info ya cargada.
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cargarDatos();
    }
}
