package org.example.clientbasket.dao.retrofit;

import org.example.clientbasket.utils.Constantes;
import org.example.common.modelo.Avisos;
import org.example.common.modelo.Usuario;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Query;

import java.util.List;

public interface UsuariosAPI {

    @GET(Constantes.USUARIO)
    Call<List<Usuario>> getAllUser();

    @PUT(Constantes.USUARIO_CAMBIAR_PASS)
    Call<Avisos> cambiarPass(@Query(Constantes.NOMBRE_USER) String nombreUser);

    @DELETE(Constantes.USUARIO)
    Call<Integer> deleteUser(@Query(Constantes.ID_USER) int idUser);

    @PUT(Constantes.USUARIO_UPDATE_ADMIN)
    Call<Avisos> hacerAdmin(@Query(Constantes.ID) int id, @Query(Constantes.CORREO) String correo);
}
