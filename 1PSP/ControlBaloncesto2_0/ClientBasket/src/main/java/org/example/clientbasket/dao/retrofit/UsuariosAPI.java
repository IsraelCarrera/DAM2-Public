package org.example.clientbasket.dao.retrofit;

import io.reactivex.rxjava3.core.Single;
import org.example.clientbasket.utils.Constantes;
import org.example.common.modelo.Avisos;
import org.example.common.modelo.Usuario;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Query;

import java.util.List;

public interface UsuariosAPI {

    @GET(Constantes.USUARIO)
    Single<List<Usuario>> getAllUser();

    @PUT(Constantes.USUARIO_CAMBIAR_PASS)
    Single<Avisos> cambiarPass(@Query(Constantes.NOMBRE_USER) String nombreUser);

    @DELETE(Constantes.USUARIO)
    Single<Integer> deleteUser(@Query(Constantes.ID_USER) int idUser);

    @PUT(Constantes.USUARIO_UPDATE_ADMIN)
    Single<Avisos> hacerAdmin(@Query(Constantes.ID) int id, @Query(Constantes.CORREO) String correo);
}
