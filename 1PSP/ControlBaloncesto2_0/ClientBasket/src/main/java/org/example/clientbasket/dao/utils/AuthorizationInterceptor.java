package org.example.clientbasket.dao.utils;


import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.example.clientbasket.utils.Constantes;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;


//No hace falta que sea singleton
public class AuthorizationInterceptor implements Interceptor {


    private final UserCacheado ca;


    @Inject
    public AuthorizationInterceptor(UserCacheado ca) {
        this.ca = ca;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        //La inicializo sin cabecera, porque si hago un registro, creo que no hay que llevar una cabecera, por mucho que lo controle en el servidor
        //Ya si hay user, pues que haga la primera autorización y posteriormente las siguientes.
        Request request = original.newBuilder().build();

        if (ca.getToken() == null) {
            if (ca.getUsuario() != null && ca.getPass() != null) {
                request = original.newBuilder()
                        .header(Constantes.AUTHORIZATION, Credentials.basic(ca.getUsuario(), ca.getPass())).build();
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
                    .header(Constantes.AUTHORIZATION, Credentials.basic(ca.getUsuario(), ca.getPass())).build();
            response = chain.proceed(request);
            if (response.header(Constantes.AUTHORIZATION) != null)
                ca.setToken(response.header(Constantes.AUTHORIZATION));
        }

        return response;
    }
}
