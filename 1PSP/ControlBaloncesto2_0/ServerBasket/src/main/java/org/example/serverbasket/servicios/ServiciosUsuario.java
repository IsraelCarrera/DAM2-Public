package org.example.serverbasket.servicios;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.example.common.modelo.UsuarioLogging;
import org.example.serverbasket.dao.DaoUsuario;
import org.example.common.error.ApiError;
import org.example.common.modelo.Usuario;
import org.example.serverbasket.dao.modelo.UsuarioEntity;

import java.util.List;

public class ServiciosUsuario {
    private final DaoUsuario dao;

    @Inject
    public ServiciosUsuario(DaoUsuario dao) {
        this.dao = dao;
    }

    public Either<ApiError, Usuario> addUser(Usuario u){
        return dao.addUser(u);
    }

    public Either<ApiError, List<Usuario>> getAllUsers(){
        return dao.getUsers();
    }

    public Either<ApiError, UsuarioEntity> getUserByCodigoActivacion(String codigo){
        return dao.getUserByCodActivacion(codigo);
    }
    public Either<ApiError,Boolean> activarUsuario(int id){
        return dao.activarCuenta(id);
    }

    public  Either<ApiError, String> MandarCodigoParaHacerAdmin(int id){
        return dao.mandarCodigoParaHacerAdmin(id);
    }

    public boolean hacerAdmin(String codigo){
        return dao.hacerAdmin(codigo);
    }

    public Either<ApiError,List<String>> cambiarCodigoPass(String nombreUser){
        return dao.escribirCodCambioPass(nombreUser);
    }

    public boolean comprobarCodigoPass(String codigo){
        return dao.comprobarCodCambiarPass(codigo);
    }

    public boolean cambiarPass(String codCambioPass,String passVieja, String nuevaPass){
        return dao.cambiarPass(codCambioPass, passVieja, nuevaPass);
    }

    public Either<ApiError,List<String>> volverActivarCuenta(String codigoViejoActivacion){
        return dao.solicitarVolverActivarCuenta(codigoViejoActivacion);
    }

    public Either<ApiError,Usuario> hacerLogging(UsuarioLogging u){
        return dao.hacerLogging(u);
    }

    public Either<ApiError, Integer> deleteUser (int usuario){
        return dao.deleteUser(usuario);
    }
}
