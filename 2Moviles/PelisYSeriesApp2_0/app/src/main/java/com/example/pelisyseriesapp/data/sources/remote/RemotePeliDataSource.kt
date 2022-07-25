package com.example.pelisyseriesapp.data.sources.remote

import com.example.pelisyseriesapp.data.modelo.RespuestaAPI
import com.example.pelisyseriesapp.data.modelo.api.PeliPorId
import com.example.pelisyseriesapp.data.toActoresActuan
import com.example.pelisyseriesapp.data.toGenerico
import com.example.pelisyseriesapp.data.toPelicula
import com.example.pelisyseriesapp.data.utils.Constantes
import javax.inject.Inject

class RemotePeliDataSource @Inject constructor(
    private val pelisApi: PelisAPI
) : RespuestaAPI() {

    suspend fun getOnePeli(id: Int) =
        safeApiCall(
            apiCall = { pelisApi.buscarPeliId(id, Constantes.ES_ES) },
            transform = PeliPorId::toPelicula
        )

    suspend fun buscarPeliculas(nombrePelicula: String) =
        safeApiCall(
            apiCall = { pelisApi.buscarPelis(nombrePelicula, Constantes.ES_ES) },
            transform = { it.results.map { pelis -> pelis.toGenerico() } })

    suspend fun buscarCastPeli(id: Int) =
        safeApiCall(apiCall = { pelisApi.buscarCastPeli(id, Constantes.ES_ES) },
            transform = { it.castPeli.map { cast -> cast.toActoresActuan() } })

}