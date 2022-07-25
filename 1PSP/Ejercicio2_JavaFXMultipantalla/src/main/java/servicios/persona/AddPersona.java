package servicios.persona;

import dao.DaoPersona;
import modelo.Persona;

public class AddPersona {
    public boolean addPersona(Persona p) {
        DaoPersona personas = new DaoPersona();
        if (p == null) {
            return false;
        } else {
            return personas.addPersona(p);
        }
    }
}
