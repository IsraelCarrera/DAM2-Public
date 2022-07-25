package com.example.pelisyseriesapp.usescases.series

import com.example.pelisyseriesapp.data.repositorio.SerieRepositorio
import javax.inject.Inject

class DeleteSerieRoomUC @Inject constructor(private val series: SerieRepositorio) {
    suspend fun invoke(idSerie: Int) = series.deleteSerie(idSerie)
}