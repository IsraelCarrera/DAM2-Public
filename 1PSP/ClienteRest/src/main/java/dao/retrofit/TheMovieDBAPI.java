package dao.retrofit;

import dao.modelos.*;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TheMovieDBAPI {
    @GET("movie/{movie_id}")
    Call<Pelicula> getPeliculaId(@Path("movie_id") int id, @Query("language") String idioma);

    @GET("movie/{movie_id}/credits")
    Call<CastPelicula> getCastPelicula(@Path("movie_id") int id, @Query("language") String idioma);

    @GET("movie/popular")
    Call<PeliculasPopulares> getPeliculasPopulares(@Query("page") int pagina, @Query("language") String idioma,
                                                   @Query("region") String region);

    @GET("collection/{collection_id}")
    Call<Colecciones> getColecciones(@Path("collection_id") int id, @Query("language") String idioma);

    @GET("person/{person_id}")
    Call<Persona> getPersonaId(@Path("person_id") int id, @Query("language") String idioma);

    @GET("tv/{tv_id}")
    Call<SerieTv> getSerieId(@Path("tv_id") int id, @Query("language") String idioma);

    @GET("tv/{tv_id}/season/{season_number}")
    Call<TemporadaTV> getTemporada(@Path("tv_id") int idTv, @Path("season_number") int numberSeason,
                                   @Query("language") String idioma);

    // No utilizada porque el cast se pilla por créditos
//    @GET("tv/{tv_id}/season/{season_number}/episode/{episode_number}")
//    Call<Episodio> getEpisodio(@Path("tv_id") int idTv, @Path("season_number") int numberSeason,
//                               @Path("episode_number") int numberEpisode, @Query("language") String idioma);

    @GET("tv/{tv_id}/season/{season_number}/episode/{episode_number}/credits")
    Call<CreditosEpisodio> getCreditosEpisodio(@Path("tv_id") int idTv, @Path("season_number") int numberSeason,
                                               @Path("episode_number") int numberEpisode, @Query("language") String idioma);


    //Al entregarla encontré en la documentación el search de pelicula (me pasa por no leer bien) y lo agrego ahora.
    @GET("search/movie")
    Call<BuscarPelicula> buscarPeliculas(@Query("query")String nombrePelicula, @Query("language")String idioma);
    @GET("search/person")
    Call<BuscarPersona> buscarPersonas(@Query("query")String nombrePersona, @Query("language") String idioma);
    @GET("search/tv")
    Call<BuscarSerie> buscarSeries(@Query("query")String nombreSerie, @Query("language") String idioma);
}
