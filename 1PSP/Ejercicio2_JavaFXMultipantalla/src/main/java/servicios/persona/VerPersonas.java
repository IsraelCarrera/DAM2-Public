package servicios.persona;

import dao.DaoPersona;
import modelo.Persona;

import java.util.List;

public class VerPersonas {
    public List<Persona> verPersonas(Boolean sexo) {
        //True si es mujer, false si es hombre.
        DaoPersona personas = new DaoPersona();
        return personas.verPersonasPorSexo(sexo);
    }
}
