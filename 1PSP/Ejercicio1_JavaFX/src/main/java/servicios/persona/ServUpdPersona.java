package servicios.persona;

import dao.DaoPersona;
import modelo.Persona;

public class ServUpdPersona implements iServiceServUpdersona {

    @Override
    public boolean modificarDatosPersona(Persona p1, Persona p2) {
        DaoPersona personas = new DaoPersona();
        return personas.modificarPersona(p1, p2);
    }
}
