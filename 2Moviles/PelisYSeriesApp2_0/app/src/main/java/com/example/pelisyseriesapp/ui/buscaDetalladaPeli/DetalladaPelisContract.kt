package com.example.pelisyseriesapp.ui.buscaDetalladaPeli

import com.example.pelisyseriesapp.domain.Pelicula
import com.example.pelisyseriesapp.utils.Constantes

interface DetalladaPelisContract {
    sealed class DetalladaPelisEvent {
        data class BuscarPeli(val idPeli: Int) : DetalladaPelisEvent()
        data class AddPeliFavorita(val idPeli: Int) : DetalladaPelisEvent()
        data class UpdatePeli(val pelicula: Pelicula) : DetalladaPelisEvent()
        object ErrorMostrado : DetalladaPelisEvent()
    }

    data class DetalladaPelisState(
        val pelicula: Pelicula = Pelicula(0,
            Constantes.VACIO,
            Constantes.VACIO,
            Constantes.VACIO,
            emptyList(),
            Constantes.VACIO,
            Constantes.VACIO,
            Constantes.VACIO,
            false,
            false),
        val isLoading: Boolean = false,
        val error: String? = null,
    )
}