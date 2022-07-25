package com.example.examen2ev.usescases

import com.example.examen2ev.data.repositorio.PokemonRepositorio
import com.example.examen2ev.domain.Pokemon
import javax.inject.Inject

class AddPokemonUC @Inject constructor(private val poke: PokemonRepositorio) {
    fun invoke(pokemon:Pokemon) = poke.addPokemon(pokemon)
}