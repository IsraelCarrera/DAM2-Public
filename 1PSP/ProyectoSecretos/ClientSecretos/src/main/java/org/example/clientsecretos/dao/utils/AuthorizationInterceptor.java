package org.example.clientsecretos.dao.utils;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AuthorizationInterceptor implements Interceptor {

    private final UserCacheado ca;


    @Inject
    public AuthorizationInterceptor(UserCacheado ca) {
        this.ca = ca;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request original = chain.request();
        //La inicializo sin cabecera, porque si hago un registro, creo que no hay que llevar una cabecera, por mucho que lo controle en el servidor
        //Ya si hay user, pues que haga la primera autorización y posteriormente las siguientes.
        Request request = original.newBuilder().build();

        String userCert = ca.getNombre() + Constantes.DOSPUNTOS + ca.getCertificadoBase64();
        byte[] userCertBytes = userCert.getBytes(StandardCharsets.UTF_8);
        if (ca.getToken() == null) {
            if (ca.getNombre() != null && ca.getPass() != null) {
                request = original.newBuilder()
                        .header(Constantes.AUTHORIZATION, Constantes.CERT + Base64.getUrlEncoder().encodeToString(userCertBytes)).build();
            }
        } else {
            request = original.newBuilder()
                    .header(Constantes.AUTHORIZATION, Constantes.BEARER + ca.getToken()).build();

        }

        Response response = chain.proceed(request);
        if (response.header(Constantes.AUTHORIZATION) != null)
            ca.setToken(response.header(Constantes.AUTHORIZATION));
        if (!response.isSuccessful() && response.header(Constantes.EXPIRES) != null) {
            response.close();
            request = original.newBuilder()
                    .header(Constantes.AUTHORIZATION, Constantes.CERT + ca.getCertificadoBase64()).build();
            response = chain.proceed(request);
            if (response.header(Constantes.AUTHORIZATION) != null)
                ca.setToken(response.header(Constantes.AUTHORIZATION));
        }

        return response;
    }
}
