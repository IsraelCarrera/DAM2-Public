package servicios.persona;

import dao.DaoPersona;
import modelo.Persona;

public class ServAddPersona implements iServiceAddPersona {
    @Override
    public boolean addPersona(Persona p) {
        DaoPersona personas = new DaoPersona();
        return personas.addPersona(p);
    }
}
