package org.example.serverserie.servicios;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.example.common.modelo.ApiError;
import org.example.common.modelo.Usuario;
import org.example.serverserie.dao.DaoUsuarios;
import org.example.serverserie.ee.security.HashPass;
import org.example.serverserie.model.UsuarioServer;

import java.time.LocalDate;

public class ServiciosUsuarios {
    private final DaoUsuarios dao;
    private final HashPass hash;

    @Inject
    public ServiciosUsuarios(DaoUsuarios dao, HashPass hash) {
        this.dao = dao;
        this.hash = hash;
    }

    public Either<ApiError, UsuarioServer> addUser(Usuario usuario){
        //Hashear la pass
        String passHasheada = hash.hashearPass(usuario.getPass());
        return dao.addUser(UsuarioServer.builder().nombre(usuario.getNombre())
                .passHash(passHasheada)
                .tipo(usuario.getTipo())
                .build());
    }

    public Either<ApiError,UsuarioServer> getUser(String nombre, String pass){
        Either<ApiError,UsuarioServer> resultado;
        Either<ApiError,UsuarioServer> getUsuario = dao.getUsuario(nombre);
        if(getUsuario.isRight()){
            //Aqui comprobamos si la contraseña es válida. Si es válida, le devolvemos el usuario, sino, apierror.
            if(hash.comprobarPass(pass,getUsuario.get().getPassHash())){
                resultado = Either.right(getUsuario.get());
            }else{
                resultado = Either.left(ApiError.builder().fecha(LocalDate.now()).mensaje("Usuario y/o contraseña no validos").build());
            }
        }else{
            resultado = Either.left(getUsuario.getLeft());
        }
        return resultado;
    }

    //FALTA DESDE AQUI.


}
