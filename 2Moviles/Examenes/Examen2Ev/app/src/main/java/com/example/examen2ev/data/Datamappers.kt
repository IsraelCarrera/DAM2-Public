package com.example.examen2ev.data

import com.example.examen2ev.domain.Pokemon
import com.example.examen2ev.domain.PokemonList
import com.example.examen2ev.data.modelo.PokemonRetrofit

fun PokemonRetrofit.toPokemonLista(): PokemonList{
    return PokemonList(
        this.id,
        this.nombre
    )
}

fun PokemonRetrofit.toPokemon(): Pokemon{
    return Pokemon(
        this.id,
        this.nombre,
        this.generacion,
        this.habilidades,
        this.caracteristicasInutiles
    )
}

fun Pokemon.toPokemonRetrofit(): PokemonRetrofit {
    return PokemonRetrofit(
        id= this.id,
        nombre = this.nombre,
        generacion = this.generacion,
        habilidades = this.habilidades,
        caracteristicasInutiles = this.caracteristicasInutiles
    )
}