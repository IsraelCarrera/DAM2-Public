package com.example.examen2ev.usescases

import com.example.examen2ev.data.repositorio.PokemonRepositorio
import javax.inject.Inject

class GetAllPokemonUC @Inject constructor(private val poke: PokemonRepositorio) {
    fun invoke() = poke.getAllPokemons()
}