package org.example.clientsecretos.dao.retrofit;

import org.example.clientsecretos.dao.utils.Constantes;
import org.example.common.modelo.Info;
import org.example.common.modelo.SecretosCommon;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.List;

public interface SecretosAPI {

    @POST(Constantes.SECRETOS)
    Call<Info> addSecreto(@Body SecretosCommon secretos);

    @GET(Constantes.SECRETOS)
    Call<List<SecretosCommon>> getAllSecretos(@Query(Constantes.NOMBRE_USER) String nombre);

    @POST(Constantes.SECRETOS_COMPARTIR)
    Call<Info> compartirSecretos(@Body SecretosCommon secretosCommon);
}
