package com.example.pelisyseriesapp.usescases.series

import com.example.pelisyseriesapp.data.repositorio.SerieRepositorio
import com.example.pelisyseriesapp.domain.Serie
import javax.inject.Inject

class DeleteSerieRoomUC @Inject constructor(private val series: SerieRepositorio) {
    suspend fun invoke(serie: Serie) = series.deleteSerie(serie)
}