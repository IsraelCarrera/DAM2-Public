package org.example.clientbasket.dao;


import com.google.gson.Gson;
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


    public Either<String, Usuario> addUsuario(Usuario u) {
        return this.construirEither(registroAPI.addUser(u));
    }

    public Either<String, Usuario> hacerLogging(String nombreUser, String pass) {
        return this.construirEither(registroAPI.hacerLogging(nombreUser, pass));
    }

    public Either<String, List<Usuario>> getAllUser() {
        return this.construirEither(usuariosAPI.getAllUser());
    }

    public Either<String, Avisos> cambiarPass(String nombreUser) {
        return this.construirEither(usuariosAPI.cambiarPass(nombreUser));
    }

    public Either<String, Integer> deleteUser(int idUser) {
        return this.construirEither(usuariosAPI.deleteUser(idUser));
    }

    public Either<String, Avisos> hacerAdmin(int id, String correo) {
        return this.construirEither(usuariosAPI.hacerAdmin(id, correo));
    }
}
