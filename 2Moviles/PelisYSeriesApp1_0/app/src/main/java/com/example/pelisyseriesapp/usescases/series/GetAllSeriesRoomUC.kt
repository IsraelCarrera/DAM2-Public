package com.example.pelisyseriesapp.usescases.series

import com.example.pelisyseriesapp.data.repositorio.SerieRepositorio
import javax.inject.Inject

class GetAllSeriesRoomUC @Inject constructor(private val series: SerieRepositorio) {
    suspend fun invoke() = series.getAllSeriesRoom()
}