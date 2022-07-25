package org.example.clientserie.dao.utils;

import com.google.gson.*;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

// PARA LLAMAR SIN INYECCIÓN
public class Producers {
    private static OkHttpClient clientOK;
    private static Retrofit retrofit;


    private Producers() {
    }

    public static synchronized Gson createGson() {
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

    public static synchronized Retrofit getInstance() {
        if (clientOK == null) {
            clientOK = new OkHttpClient.Builder()
                    .readTimeout(Duration.of(10, ChronoUnit.MINUTES))
                    .callTimeout(Duration.of(10, ChronoUnit.MINUTES))
                    .connectTimeout(Duration.of(10, ChronoUnit.MINUTES))
                    .addInterceptor(new AuthorizationInterceptor())
                    .build();

            //Para parsear las fechas.

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://localhost:8080/ServerSerie-1.0-SNAPSHOT/api/")
                    .addConverterFactory(GsonConverterFactory.create(createGson()))
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .client(clientOK)
                    .build();
        }
        return retrofit;
    }
    // PARA LLAMAR SIN INYECCIÓN
    //TheMovieDBAPI consulta = ConfigurationSingletonMovies.getInstance().create(TheMovieDBAPI.class);
}
