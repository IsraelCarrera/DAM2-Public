package com.example.pelisyseriesapp.data.sources.remote

import com.example.pelisyseriesapp.data.modelo.api.BuscarCreditosPelicula
import com.example.pelisyseriesapp.data.modelo.api.BuscarPeliculas
import com.example.pelisyseriesapp.data.modelo.api.PeliPorId
import com.example.pelisyseriesapp.data.utils.Constantes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PelisAPI {

    @GET(Constantes.LLAMADA_MOVIE)
    suspend fun buscarPeliId(
        @Path(Constantes.MOVIE_ID) id: Int,
        @Query(Constantes.IDIOMA) idioma: String
    ): Response<PeliPorId>

    @GET(Constantes.MOVIE_CREDITOS)
    suspend fun buscarCastPeli(
        @Path(Constantes.MOVIE_ID) id: Int,
        @Query(Constantes.IDIOMA) idioma: String
    ): Response<BuscarCreditosPelicula>

    @GET(Constantes.SEARCH_MOVIE)
    suspend fun buscarPelis(
        @Query(Constantes.QUERY) nombrePelicula: String,
        @Query(Constantes.IDIOMA) idioma: String
    ): Response<BuscarPeliculas>
}