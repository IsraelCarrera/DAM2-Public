package com.example.appcompose.ui.listarGatos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appcompose.ui.utils.Constantes
import com.example.appcompose.usescases.DeleteGato
import com.example.appcompose.usescases.ListGatos
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListarGatosViewModel @Inject constructor(
    private val listGatos: ListGatos,
    private val deleteGato: DeleteGato,
) : ViewModel() {
    init {
        handleEvent(ListarGatosContract.ListarGatosEvent.BuscarGatos)
    }

    private val _uiState: MutableStateFlow<ListarGatosContract.ListarGatosState> by lazy {
        MutableStateFlow(ListarGatosContract.ListarGatosState())
    }
    val uiState: StateFlow<ListarGatosContract.ListarGatosState> = _uiState

    fun handleEvent(event: ListarGatosContract.ListarGatosEvent) {
        when (event) {
            ListarGatosContract.ListarGatosEvent.BuscarGatos -> {
                viewModelScope.launch {
                    listGatos.invoke()
                        .catch(action = { cause -> _uiState.update { it.copy(error = cause.message) } })
                        .collect { result ->
                            _uiState.update {
                                it.copy(
                                    gatos = result
                                )
                            }
                        }
                }
            }
            ListarGatosContract.ListarGatosEvent.ErrorMostrado -> {
                _uiState.update { it.copy(error = null) }
            }
            is ListarGatosContract.ListarGatosEvent.DeleteGato -> {
                viewModelScope.launch {
                    try {
                        deleteGato.invoke(event.idGato)
                        _uiState.update { it.copy(mensaje = Constantes.BORRADO_EXITO) }
                    } catch (e: Exception) {
                        _uiState.update { it.copy(error = e.message) }
                    }
                }
            }
            ListarGatosContract.ListarGatosEvent.MensajeMostrado -> {
                _uiState.update { it.copy(mensaje = null) }
            }
        }
    }
}