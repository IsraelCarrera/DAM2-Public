package com.example.pelisyseriesapp.data.sources.local

import com.example.pelisyseriesapp.data.*
import com.example.pelisyseriesapp.domain.Episodio
import com.example.pelisyseriesapp.domain.Serie
import com.example.pelisyseriesapp.domain.Temporada
import javax.inject.Inject

class LocalSerieDataSource @Inject constructor(private val dao: SerieLocalDao) {
    //Idem que en pelis, mapeo aquí porque así en el repositorio lo tengo too con el mismo objeto.

    suspend fun addSerie(serie: Serie) = dao.addSerie(serie.toSerieRoom())
    suspend fun getAllSeries() = dao.getAllSeries().map { it.toFavoritos() }
    suspend fun getOneSerie(id: Int) = dao.getOneSerie(id).map { it.toSerie() }
    suspend fun updateSerie(serie: Serie) = dao.updateSerie(serie.toSerieEntidad())
    suspend fun deleteSerie(serie: Serie) = dao.deleteSerie(serie.toSerieRoom())
    suspend fun updateTemporada(temporada: Temporada) =
        dao.updateTemporada(temporada.toTemporadaEntidad())

    suspend fun updateEpisodio(episodio: Episodio) =
        dao.updateEpisodio(episodio.toCapituloEntidad())
}