package servicios;

import dao.DaoPersona;
import dao.modelos.Persona;
import dao.modelos.PersonaSacadaDeBuscar;
import io.vavr.control.Either;

import java.util.List;

public class PersonaServicios {

    public Either<String,Persona> personaPorId(int id) {
        DaoPersona dao = new DaoPersona();
        return dao.getPersonaId(id);
    }

    public Either<String, List<PersonaSacadaDeBuscar>> buscarPersonas(String nombrePersona) {
        DaoPersona dao = new DaoPersona();
        return dao.buscarPersonas(nombrePersona);
    }
}
