package com.example.examen2ev.ui.detallespokemon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examen2ev.data.modelo.ResultadoLlamada
import com.example.examen2ev.usescases.GetOnePokemonUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetallesPokemonViewModel @Inject constructor(
    private val getOnePokemon: GetOnePokemonUC
): ViewModel() {
    private val _uiState: MutableStateFlow<DetallesPokemonContract.DetallesPokemonState> by lazy {
        MutableStateFlow(DetallesPokemonContract.DetallesPokemonState())
    }
    val uiState: StateFlow<DetallesPokemonContract.DetallesPokemonState> = _uiState

    fun handleEvent(event: DetallesPokemonContract.DetallesPokemonEvent){
        when(event){
            is DetallesPokemonContract.DetallesPokemonEvent.BuscarPokemonPorId -> {
                viewModelScope.launch {
                    getOnePokemon.invoke(event.idPokemon)
                        .catch(action = { cause -> _uiState.update { it.copy(error = cause.message) } })
                        .collect { result ->
                            when(result){
                                is ResultadoLlamada.Error -> {
                                    _uiState.update { it.copy(error = result.message) }
                                }
                                is ResultadoLlamada.Success -> {
                                    _uiState.update {
                                        it.copy(
                                            pokemon = result.data)
                                    }
                                }
                            }
                        }
                }
            }
            DetallesPokemonContract.DetallesPokemonEvent.ErrorMostrado -> {
                _uiState.update { it.copy(error = null) }
            }
        }
    }
}