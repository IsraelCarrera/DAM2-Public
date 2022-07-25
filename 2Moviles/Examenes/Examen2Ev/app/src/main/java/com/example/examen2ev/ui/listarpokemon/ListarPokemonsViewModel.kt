package com.example.examen2ev.ui.listarpokemon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examen2ev.data.modelo.ResultadoLlamada
import com.example.examen2ev.usescases.GetAllPokemonUC
import com.example.examen2ev.usescases.GetPokemonPorGeneracionUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListarPokemonsViewModel @Inject constructor(
    private val getPokemons : GetAllPokemonUC,
    private val getPokemonsGeneracion: GetPokemonPorGeneracionUC
) : ViewModel() {
    init{
        handleEvent(ListarPokemonsContract.ListarPokemonsEvent.BuscarPokemons)
    }
    private val _uiState: MutableStateFlow<ListarPokemonsContract.ListarPokemonsState> by lazy{
        MutableStateFlow(ListarPokemonsContract.ListarPokemonsState())
    }
    val uiState: StateFlow<ListarPokemonsContract.ListarPokemonsState> = _uiState

    fun handleEvent(event: ListarPokemonsContract.ListarPokemonsEvent){
        when(event){
            ListarPokemonsContract.ListarPokemonsEvent.BuscarPokemons -> {
                viewModelScope.launch {
                    getPokemons.invoke()
                        .catch(action = { cause -> _uiState.update { it.copy(error = cause.message) } })
                        .collect { result ->
                            when(result){
                                is ResultadoLlamada.Error -> {
                                    _uiState.update { it.copy(error = result.message) }
                                }
                                is ResultadoLlamada.Success -> {
                                    _uiState.update {
                                        it.copy(
                                            pokemons = result.data!!
                                        )
                                    }
                                }
                            }
                        }

                }
            }
            ListarPokemonsContract.ListarPokemonsEvent.ErrorMostrado -> {
                _uiState.update { it.copy(error = null) }
            }
            is ListarPokemonsContract.ListarPokemonsEvent.GetPokemonGeneracion -> {
                val gen = event.gen
                if(gen != null){
                    viewModelScope.launch {
                        getPokemonsGeneracion.invoke(gen)
                            .catch(action = { cause -> _uiState.update { it.copy(error = cause.message) } })
                            .collect { result ->
                                when(result){
                                    is ResultadoLlamada.Error -> {
                                        _uiState.update { it.copy(error = result.message) }
                                    }
                                    is ResultadoLlamada.Success -> {
                                        _uiState.update {
                                            it.copy(
                                                pokemons = result.data!!
                                            )
                                        }
                                    }
                                }
                            }

                    }
                }else{
                    _uiState.update { it.copy(error = "No se ha podido buscar generación porque es un campo vacio/nulo") }
                }
            }
        }
    }
}