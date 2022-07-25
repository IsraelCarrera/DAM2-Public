package com.example.pelisyseriesapp.data.sources.remote

import com.example.pelisyseriesapp.data.modelo.RespuestaAPI
import com.example.pelisyseriesapp.data.modelo.api.SeriePorId
import com.example.pelisyseriesapp.data.toActoresActuan
import com.example.pelisyseriesapp.data.toEpisodio
import com.example.pelisyseriesapp.data.toGenerico
import com.example.pelisyseriesapp.data.toSerie
import com.example.pelisyseriesapp.data.utils.Constantes
import javax.inject.Inject


class RemoteSerieDataSource @Inject constructor(
    private val seriesAPI: SeriesAPI
) : RespuestaAPI() {

    suspend fun buscarSeries(nombreSerie: String) =
        safeApiCall(
            apiCall = { seriesAPI.buscarSeries(nombreSerie, Constantes.ES_ES) },
            transform = { it.resultadoBuscarSeries.map { series -> series.toGenerico() } })

    suspend fun getSerieId(id: Int) =
        safeApiCall(
            apiCall = { seriesAPI.buscarSerieId(id, Constantes.ES_ES) },
            transform = SeriePorId::toSerie
        )

    suspend fun getCreditosSerie(id: Int) =
        safeApiCall(
            apiCall = { seriesAPI.buscarCastSerie(id, Constantes.ES_ES) },
            transform = { it.cast.map { cast -> cast.toActoresActuan() } }
        )

    suspend fun getEpisodios(id: Int, numSeason: Int) =
        safeApiCall(
            apiCall = { seriesAPI.buscarTemporadaSerie(id, numSeason, Constantes.ES_ES) },
            transform = { it.episodes.map { episode -> episode.toEpisodio(id) } }
        )
}