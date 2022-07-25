package org.example.clientserie.dao.utils;


import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;


public class AuthorizationInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        //La inicializo sin cabecera, porque si hago un registro, creo que no hay que llevar una cabecera, por mucho que lo controle en el servidor
        //Ya si hay user, pues que haga la primera autorización y posteriormente las siguientes.
        Request request = original.newBuilder().build();

        if (UserCacheado.token == null) {
            if (UserCacheado.usuario != null && UserCacheado.pass != null) {
                request = original.newBuilder()
                        .header(Constantes.AUTHORIZATION, Credentials.basic(UserCacheado.usuario, UserCacheado.pass)).build();
            }
        }else if(UserCacheado.token.equals("NOTOKEN")){
            request = original.newBuilder().build();
        } else {
            request = original.newBuilder()
                    .header(Constantes.AUTHORIZATION, Constantes.BEARER + UserCacheado.token).build();

        }

        //Solo un intento.
        Response response = chain.proceed(request);
        if (response.header(Constantes.AUTHORIZATION) != null)
            UserCacheado.token = (response.header(Constantes.AUTHORIZATION));
        if (!response.isSuccessful() && response.header(Constantes.EXPIRES) != null) {
            response.close();
            request = original.newBuilder()
                    .header(Constantes.AUTHORIZATION, Credentials.basic(UserCacheado.usuario, UserCacheado.pass)).build();
            response = chain.proceed(request);
            if (response.header(Constantes.AUTHORIZATION) != null)
                UserCacheado.token = (response.header(Constantes.AUTHORIZATION));
        }

        return response;
    }
}
