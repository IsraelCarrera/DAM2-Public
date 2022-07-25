package com.example.examen2ev.ui.listarpokemon

import com.example.examen2ev.domain.PokemonList

interface ListarPokemonsContract {
    sealed class ListarPokemonsEvent{
        object BuscarPokemons : ListarPokemonsEvent()
        data class GetPokemonGeneracion(val gen:String?) : ListarPokemonsEvent()
        object ErrorMostrado : ListarPokemonsEvent()
    }
    data class ListarPokemonsState(
        val pokemons: List<PokemonList> = emptyList(),
        val error: String? = null
    )
}