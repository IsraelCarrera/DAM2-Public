package com.example.pelisyseriesapp.ui.favoritos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pelisyseriesapp.usescases.pelis.DeletePeliculaRoomUC
import com.example.pelisyseriesapp.usescases.pelis.GetAllPelisRoomUC
import com.example.pelisyseriesapp.usescases.series.DeleteSerieRoomUC
import com.example.pelisyseriesapp.usescases.series.GetAllSeriesRoomUC
import com.example.pelisyseriesapp.utils.Constantes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritosViewModel @Inject constructor(
    private val getAllPelisRoomUC: GetAllPelisRoomUC,
    private val getAllSeriesRoomUC: GetAllSeriesRoomUC,
    private val deletePeliculaRoomUC: DeletePeliculaRoomUC,
    private val deleteSerieRoomUC: DeleteSerieRoomUC,
) : ViewModel() {

    private val _uiState: MutableStateFlow<FavoritosContract.FavoritosState> by lazy {
        MutableStateFlow(FavoritosContract.FavoritosState())
    }
    val uiState: StateFlow<FavoritosContract.FavoritosState> = _uiState


    fun handleEvent(event: FavoritosContract.FavoritosEvent) {
        when (event) {
            //Se crea una clase Contract donde habrá un event y un stage (emitir datos)
            //Y todas las funciones que tenia en el VM van para el event, y solo una función en el VM.
            is FavoritosContract.FavoritosEvent.GetAllFavoritos -> {
                viewModelScope.launch {
                    //Primero pelis
                    getAllPelisRoomUC.invoke()
                        .catch(action = { cause -> _uiState.update { it.copy(error = cause.message) } })
                        .collect { result ->
                            _uiState.update {
                                it.copy(
                                    pelis = result
                                )
                            }
                        }
                }
//                    //Ahora series
                viewModelScope.launch {
                    getAllSeriesRoomUC.invoke()
                        .catch(action = { cause -> _uiState.update { it.copy(error = cause.message) } })
                        .collect { result ->
                            _uiState.update {
                                it.copy(
                                    series = result
                                )
                            }
                        }
                }
            }
            is FavoritosContract.FavoritosEvent.BorrarPelicula -> {
                viewModelScope.launch {
                    try {
                        deletePeliculaRoomUC.invoke(event.idPeli)
                        _uiState.update { it.copy(mensaje = Constantes.PELI_ELIMINADA_EXITO) }
                    } catch (e: Exception) {
                        _uiState.update { it.copy(error = e.message ?: Constantes.VACIO) }
                    }
                }
            }
            is FavoritosContract.FavoritosEvent.BorrarSerie -> {
                viewModelScope.launch {
                    try {
                        deleteSerieRoomUC.invoke(event.idSerie)
                        _uiState.update { it.copy(mensaje = Constantes.SERIE_ELIMINADA_EXITO) }

                    } catch (e: Exception) {
                        _uiState.update { it.copy(error = e.message ?: Constantes.VACIO) }
                    }
                }
            }
            FavoritosContract.FavoritosEvent.ErrorMostrado -> {
                _uiState.update {
                    it.copy(
                        error = null
                    )
                }
            }
            FavoritosContract.FavoritosEvent.MensajeMostrado -> {
                _uiState.update {
                    it.copy(
                        mensaje = null
                    )
                }
            }
        }
    }

}