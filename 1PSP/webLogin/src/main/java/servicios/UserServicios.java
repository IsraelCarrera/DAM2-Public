package servicios;

import dao.DaoUsers;
import dao.modelo.Usuario;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class UserServicios {

    private final DaoUsers dao;

    @Inject
    public UserServicios(DaoUsers dao) {
        this.dao = dao;
    }

    public Either<String, Usuario> getUserByName(String name) {
        return dao.getUserByName(name);
    }

    public List<Usuario> getAll() {
        return dao.getAllUser();
    }

    public Usuario addUser(Usuario u) {
        return dao.addUser(u);
    }

    public boolean comprobarUsuarioCorrecto(Usuario u) {
        boolean esCorrecto = false;
        Either<String, Usuario> user = getUserByName(u.getNombre());
        if (user.isRight()) {
            if (user.get().getPass().equals(u.getPass())) {
                esCorrecto = true;
            }
        }
        return esCorrecto;
    }
}

