package servicios.persona;

import dao.DaoPersona;
import modelo.Persona;

import java.util.List;

public class GetPersonas {
    public List<Persona> getPersonas() {
        DaoPersona dao = new DaoPersona();
        return dao.getPersonas();
    }
}
