package dao.utils;

import com.google.gson.*;
import config.ConfigClient;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ProducesNetworks {

    private static ConfigClient producesConfig;

    @Inject
    public ProducesNetworks(ConfigClient producesConfig) {
        ProducesNetworks.producesConfig = producesConfig;
    }

    @Produces
    @Singleton
    public static OkHttpClient getOkHTTP() {
        return new OkHttpClient.Builder()
                .protocols(List.of(Protocol.HTTP_1_1))
                .readTimeout(Duration.of(10, ChronoUnit.MINUTES))
                .callTimeout(Duration.of(10, ChronoUnit.MINUTES))
                .connectTimeout(Duration.of(10, ChronoUnit.MINUTES))
                .addInterceptor(chain -> {
                            Request original = chain.request();
                            Request.Builder builder1 = original.newBuilder()
                                    .url(original.url().newBuilder()
                                            .addQueryParameter("api_key", producesConfig.getApiKeyValue())
                                            .build()
                                    );
                            Request request = builder1.build();
                            return chain.proceed(request);
                        }
                ).build();
    }

    @Produces
    public static Gson parsearGson() {
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
    public static synchronized Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(producesConfig.getPathBase())
                .addConverterFactory(GsonConverterFactory.create(parsearGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHTTP())
                .build();
    }
}
