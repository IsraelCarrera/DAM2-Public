package org.example.clientsecretos.dao.utils;

import com.google.gson.*;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import org.example.clientsecretos.config.ConfigClient;
import org.example.clientsecretos.dao.retrofit.SecretosAPI;
import org.example.clientsecretos.dao.retrofit.UserAPI;
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

public class RetrofitProducers {
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
    public UserAPI createApiUser(Retrofit retrofit) {
        return retrofit.create(UserAPI.class);
    }

    @Produces
    public SecretosAPI createApiSecretos(Retrofit retrofit) {
        return retrofit.create(SecretosAPI.class);
    }
}
