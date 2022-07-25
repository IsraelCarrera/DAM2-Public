package com.example.pelisyseriesapp.usescases.series

import com.example.pelisyseriesapp.data.repositorio.SerieRepositorio
import javax.inject.Inject

class GetCastSerieUC @Inject constructor(private val series: SerieRepositorio) {
    fun invoke(id: Int) = series.getCastSerie(id)
}