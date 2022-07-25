package servicios.persona;

import dao.DaoPersona;
import modelo.Persona;

public class ServConsultarPersona implements iServiceConsultarPersona {
    @Override
    public Persona consultarPersona(int n) {
        DaoPersona personas = new DaoPersona();
        return personas.consultarPersona(n);
    }
}
