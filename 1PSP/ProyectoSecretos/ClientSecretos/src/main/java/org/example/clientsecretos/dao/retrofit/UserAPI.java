package org.example.clientsecretos.dao.retrofit;

import org.example.clientsecretos.dao.utils.Constantes;
import org.example.common.modelo.User;
import org.example.common.modelo.UsuarioCrear;
import org.example.common.modelo.UsuarioLoggear;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.List;

public interface UserAPI {

    @POST(Constantes.USUARIO)
    Call<UsuarioLoggear> addUser(@Body UsuarioCrear usuarioCrear);

    @GET(Constantes.USUARIO_LOG)
    Call<User> hacerLoggin(@Query(Constantes.NOMBRE_USER) String nombre, @Query(Constantes.CERTIFICADO) String certificadoBase64);

    @GET(Constantes.USUARIO)
    Call<List<User>> getAllUsers();
}
