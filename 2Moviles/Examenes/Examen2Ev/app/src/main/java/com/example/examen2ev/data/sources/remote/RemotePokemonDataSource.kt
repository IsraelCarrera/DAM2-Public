package com.example.examen2ev.data.sources.remote

import com.example.examen2ev.data.modelo.LlamadaAPI
import com.example.examen2ev.data.toPokemon
import com.example.examen2ev.data.toPokemonLista
import com.example.examen2ev.data.toPokemonRetrofit
import com.example.examen2ev.domain.Pokemon
import javax.inject.Inject

class RemotePokemonDataSource @Inject constructor(
    private val pokemonAPI: PokemonAPI,
): LlamadaAPI() {

    suspend fun getAllPokemons() =
        safeApiCall(
            apiCall = {pokemonAPI.getAllPokemons()},
            transform = {it.map { pokemon -> pokemon.toPokemonLista() }}
        )
    suspend fun getOnePokemon(id:Int) =
        safeApiCall(
            apiCall = {pokemonAPI.getOnePokemon(id)},
            transform = {it.toPokemon()}
        )

    suspend fun addPokemon(pokemon: Pokemon) =
        safeApiCall(
            apiCall = {pokemonAPI.addPokemon(pokemon.toPokemonRetrofit())},
            transform = {}
        )

    suspend fun getPokemonPorGeneracion(gen:String) =
        safeApiCall(
            apiCall = {pokemonAPI.getPokemonsPorGeneracion(gen)},
            transform = {it.map { pokemon -> pokemon.toPokemonLista() }}
        )
}