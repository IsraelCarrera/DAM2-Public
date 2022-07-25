package com.example.pelisyseriesapp.usescases.series

import com.example.pelisyseriesapp.data.repositorio.SerieRepositorio
import com.example.pelisyseriesapp.domain.Serie
import javax.inject.Inject

class UpdateSerieUC @Inject constructor(private val serie: SerieRepositorio) {
    suspend fun invoke(updateSerie: Serie) = serie.updateSerie(updateSerie)
}