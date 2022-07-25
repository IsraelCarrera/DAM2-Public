package modelo;

import java.time.LocalDate;


public class Persona {
    private String nombre;
    private int edad;
    private LocalDate fechaRegistro;
    private boolean esMujer;

    //Constructor
    public Persona() {
    }

    //G&S
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public boolean isEsMujer() {
        return esMujer;
    }

    public void setEsMujer(boolean esMujer) {
        this.esMujer = esMujer;
    }

    @Override
    public String toString() {
        String s = nombre + " " + edad + " " + fechaRegistro + " ";
        if (this.esMujer) {
            s += ("mujer");
        } else {
            s += ("hombre");
        }
        return s;
    }
}
