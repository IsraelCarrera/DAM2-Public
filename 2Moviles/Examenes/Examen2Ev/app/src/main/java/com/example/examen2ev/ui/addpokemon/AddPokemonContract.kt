package com.example.examen2ev.ui.addpokemon

import com.example.examen2ev.domain.Pokemon

interface AddPokemonContract {
    sealed class AddPokemonEvent{
        data class AddPokemon(val poke:Pokemon?) : AddPokemonEvent()
        object ErrorMostrado : AddPokemonEvent()
        object MensajeMostrado : AddPokemonEvent()
    }

    data class AddPokemonState(
        val error: String? = null,
        val mensaje: String? = null,
    )
}