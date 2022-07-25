package servicios;

import EE.errores.ApiError;
import dao.DaoEmpresa;
import dao.modelo.Empresa;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class EmpresaServicios {
    private final DaoEmpresa dao;

    @Inject
    public EmpresaServicios(DaoEmpresa dao) {
        this.dao = dao;
    }

    public Either<ApiError, Empresa> getEmpresa(int id) {
        return dao.getEmpresa(id);
    }

    public Either<ApiError, List<Empresa>> getAllEmpresas() {
        return dao.getAll();
    }

    public Empresa addEmpresa(Empresa e) {
        return dao.addEmpresa(e);
    }

    public boolean borrarEmpresa(int id) {
        return dao.borrarEmpresa(id);
    }

    public Either<ApiError, Empresa> updateEmpresa(Empresa e) {
        return dao.updateEmpresa(e);
    }

    //Funciones que pasan al servicio de Trabajador, para agregar, update y delete trabajadores.
    public void addTrabajadorEmpresa(int id, String nombre) {
        dao.meterTrabajadorEnEmpresa(id, nombre);
    }

    public void deleteTrabajadorEmpresa(int id, String nombre) {
        dao.deleteTrabajadorEmpresa(id, nombre);
    }

    public void UpdateTrabajadorEmpresa(int id, String nombreNuevo, String nombreViejo) {
        dao.updateTrabajadorEmpresa(id, nombreNuevo, nombreViejo);
    }
}
