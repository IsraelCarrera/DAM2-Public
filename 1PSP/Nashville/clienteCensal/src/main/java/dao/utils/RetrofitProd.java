package dao.utils;

import com.google.gson.*;
import config.ConfigClient;
import dao.retrofit.DefuncionesAPI;
import dao.retrofit.EmparejarAPI;
import dao.retrofit.NacimientosAPI;
import dao.retrofit.PersonasAPI;
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

public class RetrofitProd {
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
    public PersonasAPI createApiPersonas(Retrofit retrofit) {
        return retrofit.create(PersonasAPI.class);
    }

    @Produces
    public EmparejarAPI createApiEmparejar(Retrofit retrofit) {
        return retrofit.create(EmparejarAPI.class);
    }
    @Produces
    public NacimientosAPI createApiNacimientos(Retrofit retrofit) {
        return retrofit.create(NacimientosAPI.class);
    }
    @Produces
    public DefuncionesAPI createApiDefunciones(Retrofit retrofit) {
        return retrofit.create(DefuncionesAPI.class);
    }
}
