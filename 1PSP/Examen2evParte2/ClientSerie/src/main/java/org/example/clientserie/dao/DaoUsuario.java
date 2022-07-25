package org.example.clientserie.dao;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import org.example.clientserie.dao.retrofit.UsuarioAPI;
import org.example.clientserie.dao.utils.Producers;
import org.example.common.modelo.Usuario;

public class DaoUsuario extends DaoGenerico {

    public Single<Either<String, Usuario>> addUser(Usuario usuario){
        UsuarioAPI consulta = Producers.getInstance().create(UsuarioAPI.class);
        return this.construirSingle(consulta.addUser(usuario));
    }
    public Single<Either<String, Usuario>> getUserLoggin(String nombre, String pass) {
        UsuarioAPI consulta = Producers.getInstance().create(UsuarioAPI.class);
        return this.construirSingle(consulta.getUserLoggin(nombre, pass));
    }
}
