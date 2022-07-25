package org.example.clientbasket.dao;


import com.google.gson.Gson;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import org.example.clientbasket.dao.retrofit.RegistroAPI;
import org.example.clientbasket.dao.retrofit.UsuariosAPI;
import org.example.common.modelo.Avisos;
import org.example.common.modelo.Usuario;

import javax.inject.Inject;
import java.util.List;

public class DaoUsuarios extends DaoGenerico {
    private final UsuariosAPI usuariosAPI;
    private final RegistroAPI registroAPI;

    @Inject
    public DaoUsuarios(UsuariosAPI usuariosAPI, RegistroAPI registroAPI, Gson gson) {
        super(gson);
        this.usuariosAPI = usuariosAPI;
        this.registroAPI = registroAPI;
    }


    public Single<Either<String, Usuario>> addUsuario(Usuario u) {
        return this.construirSingle(registroAPI.addUser(u));
    }

    public Single<Either<String, Usuario>> hacerLogging(String nombreUser, String pass) {
        return this.construirSingle(registroAPI.hacerLogging(nombreUser, pass));
    }

    public Single<Either<String, List<Usuario>>> getAllUser() {
        return this.construirSingle(usuariosAPI.getAllUser());
    }

    public Single<Either<String, Avisos>> cambiarPass(String nombreUser) {
        return this.construirSingle(usuariosAPI.cambiarPass(nombreUser));
    }

    public Single<Either<String, Integer>> deleteUser(int idUser) {
        return this.construirSingle(usuariosAPI.deleteUser(idUser));
    }

    public Single<Either<String, Avisos>> hacerAdmin(int id, String correo) {
        return this.construirSingle(usuariosAPI.hacerAdmin(id, correo));
    }
}
