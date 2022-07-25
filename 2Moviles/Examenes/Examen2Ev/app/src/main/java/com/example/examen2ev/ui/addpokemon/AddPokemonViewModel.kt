package com.example.examen2ev.ui.addpokemon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examen2ev.data.modelo.ResultadoLlamada
import com.example.examen2ev.usescases.AddPokemonUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPokemonViewModel @Inject constructor(
    private val addPokemon: AddPokemonUC
) : ViewModel() {
    private val _uiState: MutableStateFlow<AddPokemonContract.AddPokemonState> by lazy {
        MutableStateFlow(AddPokemonContract.AddPokemonState())
    }
    val uiState: StateFlow<AddPokemonContract.AddPokemonState> = _uiState

    fun handleEvent(event: AddPokemonContract.AddPokemonEvent) {
        when (event) {
            is AddPokemonContract.AddPokemonEvent.AddPokemon -> {
                val pokemon = event.poke
                if (pokemon != null) {
                    viewModelScope.launch {
                        addPokemon.invoke(event.poke)
                            .catch(action = { cause -> _uiState.update { it.copy(error = cause.message) } })
                            .collect { result ->
                                when (result) {
                                    is ResultadoLlamada.Error -> {
                                        _uiState.update { it.copy(error = result.message) }
                                    }
                                    is ResultadoLlamada.Success -> {
                                        _uiState.update {
                                            it.copy(
                                                mensaje = "Añadido el pokemon con éxito.",
                                            )
                                        }
                                    }
                                }
                            }
                    }
                } else {
                    _uiState.update { it.copy(error = "No se ha podido añadir el pokemon, es nulo.") }
                }
            }
            AddPokemonContract.AddPokemonEvent.ErrorMostrado -> {
                _uiState.update { it.copy(error = null) }
            }
            AddPokemonContract.AddPokemonEvent.MensajeMostrado -> {
                _uiState.update {
                    it.copy(
                        mensaje = null
                    )
                }
            }
        }
    }
}