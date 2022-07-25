package com.example.pelisyseriesapp.ui.buscaDetalladaSerie

import com.example.pelisyseriesapp.domain.Serie
import com.example.pelisyseriesapp.utils.Constantes

interface DetalladaSerieContract {
    sealed class DetalladaSeriesEvent {
        data class BuscarSerie(val idSerie: Int) : DetalladaSeriesEvent()
        data class AddSerieFavorita(val idSerie: Int) : DetalladaSeriesEvent()
        data class UpdateSerie(val serie: Serie) : DetalladaSeriesEvent()
        object ErrorMostrado : DetalladaSeriesEvent()
    }

    data class DetalladaSeriesState(
        val serie: Serie = Serie(0,
            Constantes.VACIO,
            Constantes.VACIO,
            Constantes.VACIO,
            Constantes.VACIO,
            emptyList(),
            Constantes.VACIO,
            Constantes.VACIO,
            Constantes.VACIO,
            emptyList(),
            false,
            false),
        val isLoading: Boolean = false,
        val error: String? = null,
    )
}