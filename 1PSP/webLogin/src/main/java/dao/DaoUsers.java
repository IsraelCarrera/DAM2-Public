package dao;

import dao.modelo.Usuario;
import io.vavr.control.Either;
import utils.Constantes;

import java.util.ArrayList;
import java.util.List;

public class DaoUsers {

    private static final List<Usuario> usuarios = new ArrayList<>();

    static {
        usuarios.add(new Usuario("1", "root", "root"));
    }

    public List<Usuario> getAllUser() {
        return usuarios;
    }

    public Either<String, Usuario> getUserByName(String name) {
        Either<String, Usuario> resultado;
        Usuario u = usuarios.stream().filter(user -> user.getNombre().equals(name)).findFirst().orElse(null);
        if (u != null) {
            resultado = Either.right(u);
        } else {
            resultado = Either.left(Constantes.USUARIO_NO_ENCONTRADO);
        }
        return resultado;
    }

    public Usuario addUser(Usuario u) {
        u.setId(String.valueOf(Integer.parseInt(usuarios.get(usuarios.size() - 1).getId()) + 1));
        usuarios.add(u);
        return u;
    }
}
