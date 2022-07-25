package servicios.persona;

import dao.DaoPersona;
import modelo.Persona;

public class UpdatePersona {
    public boolean updatePersona(Persona personaSelect, Persona personaUpdate) {
        DaoPersona personas = new DaoPersona();
        if (personaUpdate != null) {
            return personas.modificarPersona(personaSelect, personaUpdate);
        } else {
            return false;
        }
    }
}
