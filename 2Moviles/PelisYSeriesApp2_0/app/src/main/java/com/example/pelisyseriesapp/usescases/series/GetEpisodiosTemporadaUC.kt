package com.example.pelisyseriesapp.usescases.series

import com.example.pelisyseriesapp.data.repositorio.SerieRepositorio
import javax.inject.Inject

class GetEpisodiosTemporadaUC @Inject constructor(private val serie: SerieRepositorio) {
    fun invoke(idSerie: Int, numTemporada: Int) =
        serie.getEpisodiosTemporada(idSerie, numTemporada)
}