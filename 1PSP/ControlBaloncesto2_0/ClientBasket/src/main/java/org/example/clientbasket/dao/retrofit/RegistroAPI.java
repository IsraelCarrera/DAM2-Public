package org.example.clientbasket.dao.retrofit;


import io.reactivex.rxjava3.core.Single;
import org.example.clientbasket.utils.Constantes;
import org.example.common.modelo.Usuario;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RegistroAPI {

    //AddUser
    @POST(Constantes.REGISTRO)
    Single<Usuario> addUser(@Body Usuario usuario);

    //Hacer logging.
    @GET(Constantes.REGISTRO_LOG)
    Single<Usuario> hacerLogging(@Query(Constantes.NOMBRE_USER) String nombreUser, @Query(Constantes.PASS) String pass);
}
