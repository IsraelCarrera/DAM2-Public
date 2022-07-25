package com.example.pelisyseriesapp.ui.favoritos

import com.example.pelisyseriesapp.domain.Favoritos

interface FavoritosContract {
    sealed class FavoritosEvent {
        object GetAllFavoritos : FavoritosEvent()
        data class BorrarPelicula(val idPeli: Int) : FavoritosEvent()
        data class BorrarSerie(val idSerie: Int) : FavoritosEvent()
        object ErrorMostrado : FavoritosEvent()
        object MensajeMostrado : FavoritosEvent()
    }

    data class FavoritosState(
        val pelis: List<Favoritos> = emptyList(),
        val series: List<Favoritos> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null,
        val mensaje: String? = null,
    )
}