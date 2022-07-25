package dao.utils;

import com.google.gson.*;
import config.ConfigurationSingleton;
import lombok.extern.log4j.Log4j2;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Log4j2
public class ConfigurationSingletonMovies {

    private static OkHttpClient clientOK;
    private static Retrofit retrofit;


    private ConfigurationSingletonMovies() {
    }

    //ReadTimeOut, callTimeOut, ConnectTimeOut: Su función es para "desvanacer" la llamada si no lee, no recibe, no conecta, en ese tiempo.
    public static synchronized Retrofit getInstance() {
        if (clientOK == null) {
            clientOK = new OkHttpClient.Builder()
                    .readTimeout(Duration.of(10, ChronoUnit.MINUTES))
                    .callTimeout(Duration.of(10, ChronoUnit.MINUTES))
                    .connectTimeout(Duration.of(10, ChronoUnit.MINUTES))
                    .addInterceptor(chain -> {
                                Request original = chain.request();
                                Request.Builder builder1 = original.newBuilder()
                                        .url(original.url().newBuilder()
                                                .addQueryParameter("api_key", ConfigurationSingleton.getInstance().getApi_key_value())
                                                .build()
                                        );
                                Request request = builder1.build();
                                return chain.proceed(request);
                            }
                    ).build();

            //Para parsear las fechas.
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
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

            retrofit = new Retrofit.Builder()
                    .baseUrl(ConfigurationSingleton.getInstance().getPath_base())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(clientOK)
                    .build();
        }
        return retrofit;
    }
}
