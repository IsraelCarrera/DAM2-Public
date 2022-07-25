package servicios;

import dao.DaoPersonas;
import error.ApiError;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import modelo.Persona;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class PersonasServicios {
    private final DaoPersonas dao;

    @Inject
    public PersonasServicios(DaoPersonas dao) {
        this.dao = dao;
    }

    //Personas
    public Either<ApiError, List<Persona>> getAllPersonas() {
        return dao.getAll();
    }

    public Either<ApiError, Persona> getOnePersona(String id) {
        return dao.getOnePersona(id);
    }

    public Either<ApiError, Persona> addPersona(Persona p) {
        return dao.addPersona(p);
    }

    public Either<ApiError, List<Persona>> deletePersona(String id) {

        //Lo meteré en un try/catch por si al borrar da alguna excepción, pasar el error.
        Either<ApiError, List<Persona>> resultado;
        List personasBorradas = new ArrayList();
        try {
            //Vamos primero a buscar a la persona en concreto
            Persona p = getOnePersona(id).get();
            //Vamos a contar cuantas personas borramos.
            //Primero vamos a comprobar si tiene hijos. El array está inicializado de base, así que comprobamos si está vacio.
            if (!p.getHijos().isEmpty()) {
                //Al no estar vacio, borramos todos los hijos
                for(Persona hijos: p.getHijos()){
                    personasBorradas.add(hijos);
                    dao.deletePersona(hijos.getId());
                }
            }
            //Lo siguiente es comprobar si tiene pareja. Al estar haciendolo con nulos, hay que comprobar si es nulo.
            //Si es distinto a nulo (Ergo, tiene un ID), se borra primero a la pareja.
            if (p.getIdPareja() != null) {
                personasBorradas.add(dao.getOnePersona(p.getIdPareja()).get());
                dao.deletePersona(p.getIdPareja());
            }
            //Y ya por último borramos a la persona que queremos borrar.
            personasBorradas.add(p);
            dao.deletePersona(p.getId());
            resultado = Either.right(personasBorradas);
        } catch (Exception e) {
            resultado = Either.left(new ApiError("No se ha podido borrar.", LocalDate.now()));
        }
        return resultado;
    }

    public Either<ApiError, Persona> updatePersona(Persona p) {
        return dao.updatePersona(p);
    }

    //Casamientos

    public Either<ApiError, List<Persona>> getPersonaPorSexo(String sexo) {
        return dao.getPersonaPorSexo(sexo);
    }

    public boolean realizarCasamiento(String idHombre, String idMujer) {
        boolean seHaRealizado = false;
        try {
            dao.cambiarPareja(idHombre, idMujer);
            dao.cambiarPareja(idMujer, idHombre);
            seHaRealizado = true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return seHaRealizado;
    }

    //Nacimientos

    public boolean registrarQueSonPareja(String idHombre, String idMujer) {
        boolean sonPareja;
        //Al tener el código y saberlo, con comprobar a uno es más que suficiente.
        try {
            sonPareja = dao.comprobarQueSonPareja(idHombre, idMujer);
        } catch (Exception e) {
            //La verdad que es un fallo provocado aposta, ya que lanzo la excepción para comprobarlo. He puesto el log ahí, por si acaso.
            log.error(e.getMessage(), e);
            sonPareja = false;
        }
        return sonPareja;
    }

    //Codigos que usaré aquí:
    //499 No están casados, para mandar la alert al cliente de si se ha equivocado o no, y mandarlos a una ciudad mucho mejor.
    //200 -> Usaré el OK, se ha agregado correctamente el nacimiento.
    //498 -> Algún fallo en el proceso de agregar al niño, pero SI están casados. Mandaré ApiError.
    public int registrarNacimiento(String idHombre, String idMujer, Persona bebe) {
        int resultado;
        if (registrarQueSonPareja(idHombre, idMujer)) {
            try {
                dao.registrarBebe(idHombre, idMujer, bebe);
                resultado = 200;
            } catch (Exception e) {
                resultado = 498;
            }
        } else {
            resultado = 499;
        }
        return resultado;
    }

    public boolean borrarImpuros(String idP1, String idP2) {
        return dao.deletePersona(idP1) && dao.deletePersona(idP2);
    }

    //Defunciones

    //Código 489 : Es hijo de. Se le resucitará.
    //Código 488: Fallo al registrar la defunción.
    public int registrarDefuncion(String idPersona){
        int respuesta = -1;
        //Primero vamos a comprobar si es hijo de alguien.
        if(!dao.esHijoDe(idPersona)){
            //Aquí implica que no es hijo de nadie, así que puede morir.
            try {
                dao.registrarDefuncion(idPersona);
                respuesta = 200;
            }catch (Exception e){
                log.error(e.getMessage(),e);
                respuesta = 488;
            }
        }else{
            respuesta=489;
        }
        return respuesta;
    }

    public Either<ApiError,List<Persona>> getPorFiltros(String lugarProcedencia, int anoNacimiento, int nHijos, String estadoCivil){
        return dao.getConFiltros(lugarProcedencia,anoNacimiento,nHijos,estadoCivil);
    }
}
