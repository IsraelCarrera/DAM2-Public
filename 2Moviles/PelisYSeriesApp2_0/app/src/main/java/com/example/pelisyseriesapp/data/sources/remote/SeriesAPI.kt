package com.example.pelisyseriesapp.data.sources.remote


import com.example.pelisyseriesapp.data.modelo.api.BuscarCreditosSerie
import com.example.pelisyseriesapp.data.modelo.api.BuscarSerie
import com.example.pelisyseriesapp.data.modelo.api.InfoTemporada
import com.example.pelisyseriesapp.data.modelo.api.SeriePorId
import com.example.pelisyseriesapp.data.utils.Constantes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SeriesAPI {
    @GET(Constantes.SEARCH_SERIE)
    suspend fun buscarSeries(
        @Query(Constantes.QUERY) nombreSerie: String,
        @Query(Constantes.IDIOMA) idioma: String
    ): Response<BuscarSerie>

    @GET(Constantes.LLAMADA_TV)
    suspend fun buscarSerieId(
        @Path(Constantes.TV_ID) id: Int,
        @Query(Constantes.IDIOMA) idioma: String
    ): Response<SeriePorId>

    @GET(Constantes.TV_CREDITOS)
    suspend fun buscarCastSerie(
        @Path(Constantes.TV_ID) id: Int,
        @Query(Constantes.IDIOMA) idioma: String
    ): Response<BuscarCreditosSerie>

    @GET(Constantes.TV_TEMPORADAS)
    suspend fun buscarTemporadaSerie(
        @Path(Constantes.TV_ID) id: Int,
        @Path(Constantes.SEASON_NUMBER) numSeason: Int,
        @Query(Constantes.IDIOMA) idioma: String
    ): Response<InfoTemporada>
}