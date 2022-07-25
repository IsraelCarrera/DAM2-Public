package dao.utils;

import com.google.gson.*;
import config.ConfigClient;
import dao.retrofit.PrimeraAPI;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.inject.Singleton;
import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Producers {
    @Produces
    @Singleton
    public OkHttpClient getOK() {
        return new OkHttpClient.Builder()
                .protocols(List.of(Protocol.HTTP_1_1))
                .readTimeout(Duration.of(10, ChronoUnit.MINUTES))
                .callTimeout(Duration.of(10, ChronoUnit.MINUTES))
                .connectTimeout(Duration.of(10, ChronoUnit.MINUTES))
                .build();
    }

    @Produces
    @Singleton
    @Named("pathBase")
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
    public Retrofit createRetrofit(OkHttpClient clientOk, @Named("pathBase") String pathBase, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(pathBase)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(clientOk)
                .build();
    }

    @Produces
    public PrimeraAPI createApi(Retrofit retrofit) {

        return retrofit.create(PrimeraAPI.class);
    }


}
