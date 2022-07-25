package servicios;

import dao.DaoTrabajador;
import dao.data.ApiError;
import dao.data.Trabajador;
import io.vavr.control.Either;

import javax.inject.Inject;
import java.util.List;

public class TrabajadoresServicios {
    DaoTrabajador dao;

    @Inject
    public TrabajadoresServicios(DaoTrabajador dao) {
        this.dao = dao;
    }

    public Either<ApiError, List<Trabajador>> getAllTrabajadores() {
        return dao.getAllTrabajadores();
    }

    public Either<ApiError, Trabajador> getTrabajadorId(int id) {
        return dao.getTrabajadorId(id);
    }

    public Either<ApiError, List<Trabajador>> getAllTrabajadoresPorIdEmpresa(int idEmpresa) {
        return dao.getAllTrabajadoresPorIdEmpresa(idEmpresa);
    }

    public Either<ApiError, Trabajador> addTrabajador(Trabajador t) {
        return dao.postTrabajador(t);
    }

    public Either<ApiError, Boolean> deleteTrabajador(int id) {
        return dao.deleteTrabajador(id);
    }

    public Either<ApiError, Trabajador> updateTrabajador(Trabajador t) {
        return dao.putTrabajador(t);
    }
}
