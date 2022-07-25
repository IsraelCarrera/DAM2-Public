package com.example.pelisyseriesapp.usescases.series

import com.example.pelisyseriesapp.data.repositorio.SerieRepositorio
import com.example.pelisyseriesapp.domain.Temporada
import javax.inject.Inject

class UpdateTemporadaUC @Inject constructor(private val series: SerieRepositorio) {
    suspend fun invoke(temporada: Temporada) = series.updateTemporada(temporada)
}