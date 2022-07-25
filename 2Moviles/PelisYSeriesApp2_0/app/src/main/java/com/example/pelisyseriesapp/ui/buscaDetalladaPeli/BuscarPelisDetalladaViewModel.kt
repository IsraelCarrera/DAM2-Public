package com.example.pelisyseriesapp.ui.buscaDetalladaPeli

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pelisyseriesapp.data.utils.ResultadoLlamada
import com.example.pelisyseriesapp.domain.ActoresActuan
import com.example.pelisyseriesapp.domain.Pelicula
import com.example.pelisyseriesapp.usescases.pelis.*
import com.example.pelisyseriesapp.utils.Constantes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BuscarPelisDetalladaViewModel @Inject constructor(
    private val buscarPeliPorIdUC: BuscarPeliPorIdUC,
    private val buscarCastPeliUC: BuscarCastPeliUC,
    private val getOnePeliculaRoomUC: GetOnePeliculaRoomUC,
    private val addPeliRoomUC: AddPeliRoomUC,
    private val updatePeliculaUC: UpdatePeliculaUC,
) : ViewModel() {

    private val _uiState: MutableStateFlow<DetalladaPelisContract.DetalladaPelisState> by lazy {
        MutableStateFlow(DetalladaPelisContract.DetalladaPelisState())
    }
    val uiState: StateFlow<DetalladaPelisContract.DetalladaPelisState> = _uiState


    fun handleEvent(event: DetalladaPelisContract.DetalladaPelisEvent) {
        when (event) {
            is DetalladaPelisContract.DetalladaPelisEvent.AddPeliFavorita -> {
                var peli: Pelicula? = null
                var cast: List<ActoresActuan> = emptyList()
                viewModelScope.launch {
                    buscarCastPeliUC.invoke(event.idPeli)
                        .catch(action = { cause -> _uiState.update { it.copy(error = cause.message) } })
                        .collect { result ->
                            when (result) {
                                is ResultadoLlamada.Error ->
                                    _uiState.update { it.copy(error = result.message) }
                                is ResultadoLlamada.Loading ->
                                    _uiState.update { it.copy(isLoading = true) }
                                is ResultadoLlamada.Success -> {
                                    _uiState.update {
                                        cast = result.data!!
                                        it.copy(
                                            isLoading = false
                                        )
                                    }
                                }
                            }
                        }
                    //Y ahora a la peli, que añadiremos un cast.
                    buscarPeliPorIdUC.invoke(event.idPeli)
                        .catch(action = { cause -> _uiState.update { it.copy(error = cause.message) } })
                        .collect { result ->
                            when (result) {
                                is ResultadoLlamada.Error ->
                                    _uiState.update { it.copy(error = result.message) }
                                is ResultadoLlamada.Loading ->
                                    _uiState.update { it.copy(isLoading = true) }
                                is ResultadoLlamada.Success -> {
                                    peli = result.data!!
                                    peli?.cast = cast
                                    _uiState.update {
                                        it.copy(
                                            isLoading = false
                                        )
                                    }
                                }
                            }
                        }
                    //Una vez tenemos a la peli construida, la agregamos.
                    if (peli != null) {
                        addPeliRoomUC.invoke(peli!!)
                    } else {
                        _uiState.update { it.copy(error = Constantes.NO_ADD_PELI) }
                    }
                }
            }
            is DetalladaPelisContract.DetalladaPelisEvent.BuscarPeli -> {
                var peli: Pelicula? = null
                var peliRoom: Pelicula? = null
                var estaEnRoom = false
                viewModelScope.launch {
                    getOnePeliculaRoomUC.invoke(event.idPeli)
                        .catch(action = { cause -> _uiState.update { it.copy(error = cause.message) } })
                        .collect { result ->
                            try {
                                if (result.isNotEmpty()) {
                                    peliRoom = result[0]
                                    estaEnRoom = true
                                }
                            } catch (e: Exception) {
                                _uiState.update { it.copy(error = e.message ?: Constantes.VACIO) }
                            }
                        }
                }
                if (!estaEnRoom) {
                    viewModelScope.launch {
                        //Si no está en room, buscamos primero el cast, y luego la peli
                        var cast: List<ActoresActuan> = emptyList()
                        buscarCastPeliUC.invoke(event.idPeli)
                            .catch(action = { cause -> _uiState.update { it.copy(error = cause.message) } })
                            .collect { result ->
                                when (result) {
                                    is ResultadoLlamada.Error ->
                                        _uiState.update { it.copy(error = result.message) }
                                    is ResultadoLlamada.Loading ->
                                        _uiState.update { it.copy(isLoading = true) }
                                    is ResultadoLlamada.Success -> {
                                        _uiState.update {
                                            cast = result.data!!
                                            it.copy(
                                                isLoading = false
                                            )
                                        }
                                    }
                                }
                            }
                        //Y ahora a la peli, que añadiremos un cast.
                        buscarPeliPorIdUC.invoke(event.idPeli)
                            .catch(action = { cause -> _uiState.update { it.copy(error = cause.message) } })
                            .collect { result ->
                                when (result) {
                                    is ResultadoLlamada.Error ->
                                        _uiState.update { it.copy(error = result.message) }
                                    is ResultadoLlamada.Loading ->
                                        _uiState.update { it.copy(isLoading = true) }
                                    is ResultadoLlamada.Success -> {
                                        peli = result.data!!
                                        peli?.cast = cast
                                        _uiState.update {
                                            it.copy(
                                                isLoading = false
                                            )
                                        }
                                    }
                                }
                            }
                        //Lo meto aquí, porqué? Por que es la rutina que más va a tardar.
                        if (peliRoom != null) {
                            _uiState.update {
                                it.copy(
                                    pelicula = peliRoom!!
                                )
                            }
                        } else {
                            _uiState.update {
                                it.copy(
                                    pelicula = peli!!
                                )
                            }
                        }
                    }

                }
            }
            is DetalladaPelisContract.DetalladaPelisEvent.UpdatePeli -> {
                viewModelScope.launch {
                    try {
                        updatePeliculaUC.invoke(event.pelicula)
                    } catch (e: Exception) {
                        _uiState.update { it.copy(error = e.message ?: Constantes.VACIO) }
                    }
                }
            }
            DetalladaPelisContract.DetalladaPelisEvent.ErrorMostrado -> {
                _uiState.update { it.copy(error = null) }
            }
        }
    }
}