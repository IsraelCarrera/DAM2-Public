package dao;

import io.vavr.control.Either;
import modelos.Persona;

import java.util.List;

public interface DAOPers {
    Either<String, Persona> getPersonaId(int id);

    Either<String, List<Persona>> buscarPersonas(String nombre);
}
