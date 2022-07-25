package com.example.appcompose.ui.detallesGato

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appcompose.usescases.DeleteGato
import com.example.appcompose.usescases.GetOneGato
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetallesGatoViewModel @Inject constructor(
    private val getOneGato: GetOneGato,
    private val deleteGato: DeleteGato,
) : ViewModel() {
    private val _uiState: MutableStateFlow<DetallesGatoContract.DetallesGatoState> by lazy {
        MutableStateFlow(DetallesGatoContract.DetallesGatoState())
    }
    val uiState: StateFlow<DetallesGatoContract.DetallesGatoState> = _uiState

    fun handleEvent(event: DetallesGatoContract.DetallesGatoEvent) {
        when (event) {
            is DetallesGatoContract.DetallesGatoEvent.BuscarGatoPorId -> {
                viewModelScope.launch {
                    getOneGato.invoke(event.idGato)
                        .catch(action = { cause -> _uiState.update { it.copy(error = cause.message) } })
                        .collect { result ->
                            _uiState.update {
                                it.copy(gato = result[0])
                            }
                        }
                }
            }
            DetallesGatoContract.DetallesGatoEvent.ErrorMostrado -> {
                _uiState.update { it.copy(error = null) }
            }
        }
    }
}