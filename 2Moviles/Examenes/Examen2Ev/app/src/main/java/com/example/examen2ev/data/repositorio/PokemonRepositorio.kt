package com.example.examen2ev.data.repositorio

import com.example.examen2ev.data.modelo.ResultadoLlamada
import com.example.examen2ev.data.sources.remote.RemotePokemonDataSource
import com.example.examen2ev.domain.Pokemon
import com.example.examen2ev.domain.PokemonList
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ActivityRetainedScoped
class PokemonRepositorio @Inject constructor(
    private val remotePokemonDataSource: RemotePokemonDataSource
) {

    fun getAllPokemons(): Flow<ResultadoLlamada<List<PokemonList>>>{
        return flow{
            emit(remotePokemonDataSource.getAllPokemons())
        }.flowOn(Dispatchers.IO)
    }

    fun getOnePokemon(id: Int): Flow<ResultadoLlamada<Pokemon>>{
        return flow{
            emit(remotePokemonDataSource.getOnePokemon(id))
        }.flowOn(Dispatchers.IO)
    }

    fun addPokemon(pokemon: Pokemon): Flow<ResultadoLlamada<Unit>>{
        return flow{
            emit(remotePokemonDataSource.addPokemon(pokemon = pokemon))
        }.flowOn(Dispatchers.IO)
    }

    fun getPokemonsGeneracion(gen:String): Flow<ResultadoLlamada<List<PokemonList>>>{
        return flow{
            emit(remotePokemonDataSource.getPokemonPorGeneracion(gen))
        }.flowOn(Dispatchers.IO)
    }
}