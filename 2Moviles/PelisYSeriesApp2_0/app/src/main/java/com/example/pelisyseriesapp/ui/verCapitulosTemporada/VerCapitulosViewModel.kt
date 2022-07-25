package com.example.pelisyseriesapp.ui.verCapitulosTemporada

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pelisyseriesapp.usescases.series.GetCapitulosTemporada
import com.example.pelisyseriesapp.usescases.series.UpdateEpisodioUC
import com.example.pelisyseriesapp.usescases.series.UpdateTemporadaUC
import com.example.pelisyseriesapp.utils.Constantes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerCapitulosViewModel @Inject constructor(
    private val updateEpisodioUC: UpdateEpisodioUC,
    private val updateTemporadaUC: UpdateTemporadaUC,
    private val getCapitulosTemporada: GetCapitulosTemporada,
) : ViewModel() {
    private val _uiState: MutableStateFlow<VerCapitulosTemporadaContract.VerCapitulosTemporadaState> by lazy {
        MutableStateFlow(VerCapitulosTemporadaContract.VerCapitulosTemporadaState())
    }
    val uiState: StateFlow<VerCapitulosTemporadaContract.VerCapitulosTemporadaState> = _uiState


    fun handleEvent(event: VerCapitulosTemporadaContract.VerCapitulosTemporadaEvent) {
        when (event) {
            is VerCapitulosTemporadaContract.VerCapitulosTemporadaEvent.GetEpisodios -> {
                viewModelScope.launch {
                    getCapitulosTemporada.invoke(event.idSerie, event.numTemporada)
                        .catch(action = { cause -> _uiState.update { it.copy(error = cause.message) } })
                        .collect { result ->
                            _uiState.update {
                                it.copy(
                                    capitulos = result
                                )
                            }
                        }
                }
            }
            is VerCapitulosTemporadaContract.VerCapitulosTemporadaEvent.UpdateEpisodio -> {
                viewModelScope.launch {
                    try {
                        updateEpisodioUC.invoke(event.episodio)
                    } catch (e: Exception) {
                        _uiState.update { it.copy(error = e.message ?: Constantes.VACIO) }
                    }
                }
            }
            is VerCapitulosTemporadaContract.VerCapitulosTemporadaEvent.UpdateTemporada -> {
                viewModelScope.launch {
                    try {
                        updateTemporadaUC.invoke(event.temporada)
                    } catch (e: Exception) {
                        _uiState.update { it.copy(error = e.message ?: Constantes.VACIO) }
                    }
                }
            }
            VerCapitulosTemporadaContract.VerCapitulosTemporadaEvent.ErrorMostrado -> {
                _uiState.update { it.copy(error = null) }
            }
        }
    }
}