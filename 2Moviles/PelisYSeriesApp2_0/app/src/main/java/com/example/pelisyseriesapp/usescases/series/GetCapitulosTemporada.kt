package com.example.pelisyseriesapp.usescases.series

import com.example.pelisyseriesapp.data.repositorio.SerieRepositorio
import javax.inject.Inject

class GetCapitulosTemporada @Inject constructor(private val series: SerieRepositorio) {
    fun invoke(idSerie: Int, numTemporada: Int) =
        series.getCapitulosTemporadaRoom(idSerie, numTemporada)
}