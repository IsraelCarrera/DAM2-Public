package dao;

import modelo.Persona;

import java.util.ArrayList;
import java.util.List;

public class DaoPersona {
    private static List<Persona> personas;

    public DaoPersona() {
        if (personas == null) {
            personas = new ArrayList<>();
        }
    }

    public static List<Persona> getPersonas() {
        return personas;
    }

    public boolean addPersona(Persona p) {
        return personas.add(p);
    }

    public boolean borrarPersona(Persona p) {
        return personas.remove(p);
    }

    public boolean modificarPersona(Persona p1, Persona p2) {
        if (personas.contains(p1)) {
            personas.set(personas.indexOf(p1), p2);
            return true;
        } else {
            return false;
        }
    }

    public Persona consultarPersona(int ind) {
        return personas.get(ind);
    }
}
