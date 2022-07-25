package org.example.clientserie.dao.retrofit;

import io.reactivex.rxjava3.core.Single;
import org.example.common.modelo.Capitulo;
import org.example.common.modelo.Serie;
import retrofit2.http.*;

import java.util.List;

public interface SeriesAPI {

    @POST("series")
    Single<Serie> addSerie(@Body Serie serie);

    @DELETE("series")
    Single<Boolean> deleteSerie(@Query("idUser") int idUser, @Query("idSerie") int idSerie);

    @POST("series/capitulo")
    Single<Capitulo> addCapitulo(@Body Capitulo capitulo);

    @DELETE("series/capitulo")
    Single<Boolean> deleteCapitulo(@Query("idUser") int idUser, @Query("idSerie") int idSerie, @Query("idCapitulo") int idCapitulo);

    @PUT("series/updateCap")
    Single<Boolean> updateCapitulo(@Query("idUser") int idUser, @Query("idSerie") int idSerie, @Query("idCapitulo") int idCapitulo, @Query("quePaso") boolean quepaso);

    @GET("series")
    Single<List<Serie>> getAllSeries(@Query("idUser") int idUser);

    @GET("series/getOneSerie")
    Single<Serie> getOneSerie(@Query("idUser") int idUser, @Query("idSerie") int idSerie);
}
