package servicios.persona;

import dao.DaoPersona;
import modelo.Persona;

public class ServDeletePersona implements iServiceDeletePersona {
    @Override
    public boolean deletePersona(Persona p) {
        DaoPersona personas = new DaoPersona();
        return personas.borrarPersona(p);
    }
}
