package servicios;

import dao.DAOPers;
import io.vavr.control.Either;
import modelos.Persona;

import javax.inject.Inject;
import java.util.List;

public class PersonaServicios {
    DAOPers dao;

    @Inject
    public PersonaServicios(DAOPers dao) {
        this.dao = dao;
    }

    public Either<String, Persona> personaPorId(int id) {
        return dao.getPersonaId(id);
    }

    public Either<String, List<Persona>> buscarPersonas(String nombrePersona) {
        return dao.buscarPersonas(nombrePersona);
    }
}
