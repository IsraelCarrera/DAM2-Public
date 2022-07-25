package servicios;

import dao.DaoPersonas;
import dao.data.PersonaData;
import io.vavr.control.Either;

import javax.inject.Inject;
import java.util.List;

public class PersonasServicios {
    private final DaoPersonas dao;

    @Inject
    public PersonasServicios(DaoPersonas dao) {
        this.dao = dao;
    }

    public Either<String, List<PersonaData>> getAllPersonas() {
        return dao.getAllPersonas();
    }

    public Either<String, PersonaData> vieneUnaPersona(PersonaData p) {
        return dao.vieneUnaPersona(p);
    }

    public Either<String, List<PersonaData>> sevaUnaPersona(String id) {
        return dao.seMudaFueraUnDesgraciado(id);
    }

    public Either<String, PersonaData> updatePersona(PersonaData p) {
        return dao.updatePersona(p);
    }

    public Either<String, List<PersonaData>> getPorFiltros(String lugarProcedencia, int anoNacimiento, int nHijos, String estadoCivil) {
        return dao.getPersonasFiltros(lugarProcedencia, anoNacimiento, nHijos, estadoCivil);
    }

    public Either<String, List<PersonaData>> getAllPersonasSexo(String sexo) {
        return dao.getPersonasPorSexo(sexo);
    }

    public Either<String, Boolean> realizarEmparejamiento(String idHombre, String idMujer) {
        return dao.casarPersonas(idHombre, idMujer);
    }

    public Either<Integer, Integer> matarPersona(String idPersona) {
        return dao.matarPersona(idPersona);
    }

    public Either<Integer, PersonaData> addBebe(String idPadre, String idMadre, PersonaData p) {
        return dao.registrarBebe(idPadre, idMadre, p);
    }

    public Either<String, Boolean> promiscuosFuera(String idP1, String idP2) {
        return dao.promiscuosFuera(idP1, idP2);
    }
}
