package com.example.examen2ev.data.sources.remote

import com.example.examen2ev.data.modelo.PokemonRetrofit
import retrofit2.Response
import retrofit2.http.*

interface PokemonAPI {
    @GET("pokemon")
    suspend fun getAllPokemons(): Response<List<PokemonRetrofit>>
    @GET("pokemon/{id}")
    suspend fun getOnePokemon(
        @Path("id") id: Int
    ): Response<PokemonRetrofit>

    @POST("pokemon")
    suspend fun addPokemon(
        @Body pokemonRetrofit: PokemonRetrofit
    ): Response<Unit>

    @GET("pokemon/filtro")
    suspend fun getPokemonsPorGeneracion(
        @Query("categoria") generacion: String
    ): Response<List<PokemonRetrofit>>
}