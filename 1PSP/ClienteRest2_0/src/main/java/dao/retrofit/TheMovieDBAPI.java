package dao.retrofit;

import dao.data.*;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TheMovieDBAPI {
    @GET("movie/{movie_id}")
    Call<PeliculaData> getPeliculaId(@Path("movie_id") int id, @Query("language") String idioma);

    @GET("movie/{movie_id}/credits")
    Call<CastPeliculaData> getCastPelicula(@Path("movie_id") int id, @Query("language") String idioma);

    @GET("movie/popular")
    Call<PeliculasPopulares> getPeliculasPopulares(@Query("page") int pagina, @Query("language") String idioma,
                                                   @Query("region") String region);

    @GET("collection/{collection_id}")
    Call<ColeccionesData> getColecciones(@Path("collection_id") int id, @Query("language") String idioma);

    @GET("person/{person_id}")
    Call<PersonaData> getPersonaId(@Path("person_id") int id, @Query("language") String idioma);

    @GET("tv/{tv_id}")
    Call<SerieTvData> getSerieId(@Path("tv_id") int id, @Query("language") String idioma);

    @GET("tv/{tv_id}/season/{season_number}")
    Call<TemporadaTVData> getTemporada(@Path("tv_id") int idTv, @Path("season_number") int numberSeason,
                                       @Query("language") String idioma);


    @GET("tv/{tv_id}/season/{season_number}/episode/{episode_number}/credits")
    Call<CreditosEpisodioData> getCreditosEpisodio(@Path("tv_id") int idTv, @Path("season_number") int numberSeason,
                                                   @Path("episode_number") int numberEpisode, @Query("language") String idioma);


    //Al entregarla encontré en la documentación el search de pelicula (me pasa por no leer bien) y lo agrego ahora.
    @GET("search/movie")
    Call<BuscarPeliculaData> buscarPeliculas(@Query("query") String nombrePelicula, @Query("language") String idioma);

    @GET("search/person")
    Call<BuscarPersonaData> buscarPersonas(@Query("query") String nombrePersona, @Query("language") String idioma);

    @GET("search/tv")
    Call<BuscarSerieData> buscarSeries(@Query("query") String nombreSerie, @Query("language") String idioma);
}
