package org.example.clientserie.dao.retrofit;

import io.reactivex.rxjava3.core.Single;
import org.example.common.modelo.Usuario;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UsuarioAPI {

    @POST("usuario")
    Single<Usuario> addUser(@Body Usuario usuario);

    @GET("usuario")
    Single<Usuario> getUserLoggin(@Query("nombre") String nombre, @Query("pass") String pass);
}
