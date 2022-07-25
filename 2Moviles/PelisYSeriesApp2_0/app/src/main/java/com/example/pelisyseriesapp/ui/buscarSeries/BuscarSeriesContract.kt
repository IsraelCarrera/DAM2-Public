package com.example.pelisyseriesapp.ui.buscarSeries

import com.example.pelisyseriesapp.domain.GenericoSeriesPelis


interface BuscarSeriesContract {
    sealed class BuscarSeriesEvent {
        data class BuscarSeries(val nombreSerie: String) : BuscarSeriesEvent()
        data class AddSerieFavorita(val idSerie: Int) : BuscarSeriesEvent()
        object ErrorMostrado : BuscarSeriesEvent()
    }

    data class BuscarSeriesState(
        val series: List<GenericoSeriesPelis> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null,
    )
}