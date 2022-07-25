package com.example.pelisyseriesapp.data.sources.local

import com.example.pelisyseriesapp.data.*
import com.example.pelisyseriesapp.data.utils.ResultadoLlamada
import com.example.pelisyseriesapp.domain.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalSerieDataSource @Inject constructor(private val dao: SerieLocalDao) {
    //Idem que en pelis, mapeo aquí porque así en el repositorio lo tengo too con el mismo objeto.

    suspend fun addSerie(serie: Serie) = dao.addSerie(serie.toSerieRoom())
    suspend fun updateSerie(serie: Serie) = dao.updateSerie(serie.toSerieEntidad())
    suspend fun deleteSerie(idSerie: Int) = dao.deleteAllSerie(idSerie)
    suspend fun updateTemporada(temporada: Temporada) =
        dao.updateTemporada(temporada.toTemporadaEntidad())

    suspend fun updateEpisodio(episodio: Episodio) =
        dao.updateEpisodio(episodio.toCapituloEntidad())

    //Las del flow
    fun getAllSeries(): Flow<List<Favoritos>> =
        dao.getAllSeries().map { series -> series.map { it.toFavoritos() } }

    fun getOneSerie(id: Int) = dao.getOneSerie(id).map { series -> series.map { it.toSerie() } }
    fun getCapitulosTemporada(idSerie: Int, numTemporada: Int) =
        dao.getEpisodiosTemporada(idSerie, numTemporada).map { cap -> cap.map { it.toCapitulos() } }

    //Caché

    suspend fun getSeriesCache(): ResultadoLlamada<List<GenericoSeriesPelis>> =
        dao.getAllSeriesCacheadas().let {
            ResultadoLlamada.Success(it.map { serie -> serie.toGenerico() })
        }

    suspend fun todoSerieCache(serie: List<GenericoSeriesPelis>) =
        dao.todoSerieCache(serie.map { it.toSerieCache() })
}