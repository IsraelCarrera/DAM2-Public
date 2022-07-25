package servicios;

import dao.DaoEmpresa;
import dao.data.ApiError;
import dao.data.Empresa;
import io.vavr.control.Either;

import javax.inject.Inject;
import java.util.List;

public class EmpresaServicios {
    DaoEmpresa dao;

    @Inject
    public EmpresaServicios(DaoEmpresa dao) {
        this.dao = dao;
    }

    public Either<ApiError, List<Empresa>> getAllEmpresas() {
        return dao.getAllEmpresas();
    }

    public Either<ApiError, Empresa> getEmpresaId(int id) {
        return dao.getEmpresaId(id);
    }

    public Either<ApiError, Empresa> addEmpresa(Empresa e) {
        return dao.postEmpresa(e);
    }

    public Either<ApiError, Boolean> deleteEmpresa(int id) {
        return dao.deleteEmpresa(id);
    }

    public Either<ApiError, Empresa> updateEmpresa(Empresa e) {
        return dao.putEmpresa(e);
    }
}
