package org.example.clientbasket.dao.utils;

import com.google.gson.*;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import org.example.clientbasket.config.ConfigClient;
import org.example.clientbasket.dao.retrofit.*;
import org.example.clientbasket.utils.Constantes;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.inject.Singleton;
import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class RetrofitProd {

    @Produces
    @Singleton
    public OkHttpClient getOK(AuthorizationInterceptor authorizationInterceptor) {
        return new OkHttpClient.Builder()
                .protocols(List.of(Protocol.HTTP_1_1))
                .readTimeout(Duration.of(10, ChronoUnit.MINUTES))
                .callTimeout(Duration.of(10, ChronoUnit.MINUTES))
                .connectTimeout(Duration.of(10, ChronoUnit.MINUTES))
                .addInterceptor(authorizationInterceptor)
                .build();
    }

    @Produces
    @Singleton
    @Named(Constantes.PATH_BASE)
    public String getPathBase(ConfigClient configClient) {
        return configClient.getPathBase();
    }

    @Produces
    @Singleton
    public Gson createGson() {
        return new GsonBuilder().registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
            @Override
            public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                LocalDate ld;
                if (json.getAsJsonPrimitive().getAsString().isEmpty()) {
                    ld = null;
                } else {
                    ld = LocalDate.parse(json.getAsJsonPrimitive().getAsString());
                }
                return ld;
            }
        }).registerTypeAdapter(LocalDate.class, new JsonSerializer<LocalDate>() {
                    @Override
                    public JsonElement serialize(LocalDate localDate, Type type, JsonSerializationContext jsonSerializationContext) {
                        return new JsonPrimitive(localDate.toString());
                    }
                }
        ).create();
    }

    @Produces
    @Singleton
    public Retrofit createRetrofit(OkHttpClient clientOk, @Named(Constantes.PATH_BASE) String pathBase, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(pathBase)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(clientOk)
                .build();
    }

    @Produces
    public EquipoAPI createApiEquipo(Retrofit retrofit) {
        return retrofit.create(EquipoAPI.class);
    }

    @Produces
    public JornadaAPI createApiJornada(Retrofit retrofit) {
        return retrofit.create(JornadaAPI.class);
    }

    @Produces
    public PartidoAPI createApiPartido(Retrofit retrofit) {
        return retrofit.create(PartidoAPI.class);
    }

    @Produces
    public RegistroAPI createApiRegistro(Retrofit retrofit) {
        return retrofit.create(RegistroAPI.class);
    }

    @Produces
    public UsuariosAPI createApiUsuario(Retrofit retrofit) {
        return retrofit.create(UsuariosAPI.class);
    }

}
