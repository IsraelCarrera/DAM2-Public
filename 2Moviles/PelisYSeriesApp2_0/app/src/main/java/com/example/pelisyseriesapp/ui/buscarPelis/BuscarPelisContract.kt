package com.example.pelisyseriesapp.ui.buscarPelis

import com.example.pelisyseriesapp.domain.GenericoSeriesPelis


interface BuscarPelisContract {
    sealed class BuscarPelisEvent {
        data class BuscarPeliculas(val nombrePeli: String) : BuscarPelisEvent()
        data class AddPeliFavorita(val idPeli: Int) : BuscarPelisEvent()
        object ErrorMostrado : BuscarPelisEvent()
    }

    data class BuscarPelisState(
        val pelis: List<GenericoSeriesPelis> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null,
    )
}