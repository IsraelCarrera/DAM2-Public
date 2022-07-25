package com.example.pelisyseriesapp.usescases.series

import com.example.pelisyseriesapp.data.repositorio.SerieRepositorio
import javax.inject.Inject

class BuscarSeriesUC @Inject constructor(private val series: SerieRepositorio) {
    fun invoke(nombreSerie: String) = series.buscarSeries(nombreSerie)
}