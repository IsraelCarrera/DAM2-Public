package com.example.appcompose.ui.addGato

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appcompose.ui.utils.Constantes
import com.example.appcompose.usescases.AddGato
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddGatoViewModel @Inject constructor(
    private val addGato: AddGato,
) : ViewModel() {
    private val _uiState: MutableStateFlow<AddGatoContract.AddGatoState> by lazy {
        MutableStateFlow(AddGatoContract.AddGatoState())
    }
    val uiState: StateFlow<AddGatoContract.AddGatoState> = _uiState

    fun handleEvent(event: AddGatoContract.AddGatoEvent) {
        when (event) {
            is AddGatoContract.AddGatoEvent.AddGato -> {
                val gato = event.gato
                viewModelScope.launch {
                    if (gato != null) {
                        addGato.invoke(gato)
                        _uiState.update {
                            it.copy(mensaje = Constantes.ADD_OK)
                        }
                    } else {
                        _uiState.update { it.copy(error = Constantes.ADD_NOOK) }
                    }
                }
            }
            AddGatoContract.AddGatoEvent.ErrorMostrado -> {
                _uiState.update { it.copy(error = null) }
            }
            AddGatoContract.AddGatoEvent.MensajeMostrado -> {
                _uiState.update {
                    it.copy(
                        mensaje = null
                    )
                }
            }
        }
    }
}