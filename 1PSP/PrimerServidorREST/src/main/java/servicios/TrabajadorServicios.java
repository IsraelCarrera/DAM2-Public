package servicios;

import EE.errores.ApiError;
import dao.DaoTrabajador;
import dao.modelo.Trabajador;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class TrabajadorServicios {
    private final DaoTrabajador dao;
    private final EmpresaServicios es;

    @Inject
    public TrabajadorServicios(DaoTrabajador dao, EmpresaServicios es) {
        this.dao = dao;
        this.es = es;
    }

    public Either<ApiError, Trabajador> getTrabajador(int id) {
        return dao.getTrabajador(id);
    }

    public Either<ApiError, List<Trabajador>> getAllTrabajadores() {
        return dao.getAll();
    }

    public Either<ApiError, List<Trabajador>> getTrabajadoresPorEmpresa(int idEmpresa) {
        return dao.getTrabajadorPorEmpresa(idEmpresa);
    }

    //Vamos tmbn a meter trabajar con el trabajador que está en la lista de empresa. Sabiendo que en el client no podrá elegir otra empresa de las que serán visibles
    //no controlo porque siempre meterá un ID de empresa correcto.
    public Trabajador addTrabajador(Trabajador t) {
        es.addTrabajadorEmpresa(t.getIdEmpresa(), t.getNombre());
        return dao.addTrabajador(t);
    }

    public boolean borrarTrabajador(int id) {
        //Necesito el nombre del trabajador, voy a llamar al get trabajador por id y le cojo el nombre.
        Trabajador t = dao.getTrabajador(id).get();
        es.deleteTrabajadorEmpresa(t.getIdEmpresa(), t.getNombre());
        return dao.borrarTrabajador(id);
    }

    public Either<ApiError, Trabajador> updateTrabajador(Trabajador t) {
        //Necesito el nombre viejo para buscarlo en el Array de string. Creo que hay mucha mierda de código, que se arreglará cuando haya una BBDD atrás y no un estático.
        es.UpdateTrabajadorEmpresa(t.getIdEmpresa(), t.getNombre(), dao.getTrabajador(t.getId()).get().getNombre());
        return dao.updateTrabajador(t);
    }
}
