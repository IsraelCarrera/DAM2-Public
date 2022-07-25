package com.example.examen2ev.ui.detallespokemon

import com.example.examen2ev.domain.Pokemon

interface DetallesPokemonContract {
    sealed class DetallesPokemonEvent {
        data class BuscarPokemonPorId(val idPokemon: Int) : DetallesPokemonEvent()
        object ErrorMostrado : DetallesPokemonEvent()
    }

    data class DetallesPokemonState(
        val pokemon: Pokemon? = null,
        val error: String? = null,
    )
}