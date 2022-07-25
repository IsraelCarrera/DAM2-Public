package com.example.pelisyseriesapp.usescases.series

import com.example.pelisyseriesapp.data.repositorio.SerieRepositorio
import com.example.pelisyseriesapp.domain.Episodio
import javax.inject.Inject

class UpdateEpisodioUC @Inject constructor(private val series: SerieRepositorio) {
    suspend fun invoke(episodio: Episodio) = series.updateEpisodio(episodio)
}