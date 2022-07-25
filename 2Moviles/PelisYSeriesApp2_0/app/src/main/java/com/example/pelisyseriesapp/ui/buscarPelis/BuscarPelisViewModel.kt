package com.example.pelisyseriesapp.ui.buscarPelis


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pelisyseriesapp.data.utils.ResultadoLlamada
import com.example.pelisyseriesapp.domain.ActoresActuan
import com.example.pelisyseriesapp.domain.GenericoSeriesPelis
import com.example.pelisyseriesapp.domain.Pelicula
import com.example.pelisyseriesapp.usescases.pelis.AddPeliRoomUC
import com.example.pelisyseriesapp.usescases.pelis.BuscarCastPeliUC
import com.example.pelisyseriesapp.usescases.pelis.BuscarPeliPorIdUC
import com.example.pelisyseriesapp.usescases.pelis.BuscarPeliculasUC
import com.example.pelisyseriesapp.utils.Constantes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BuscarPelisViewModel @Inject constructor(
    private val buscarPeliculasUC: BuscarPeliculasUC,
    private val addPeliRoomUC: AddPeliRoomUC,
    private val buscarPeliPorIdUC: BuscarPeliPorIdUC,
    private val buscarCastPeliUC: BuscarCastPeliUC,
) : ViewModel() {


    private val _uiState: MutableStateFlow<BuscarPelisContract.BuscarPelisState> by lazy {
        MutableStateFlow(BuscarPelisContract.BuscarPelisState())
    }
    val uiState: StateFlow<BuscarPelisContract.BuscarPelisState> = _uiState

    //Para el multiselección
    private var itemSeleccionados = mutableListOf<GenericoSeriesPelis>()

    fun handleEvent(event: BuscarPelisContract.BuscarPelisEvent) {
        when (event) {
            is BuscarPelisContract.BuscarPelisEvent.AddPeliFavorita -> {
                var peli: Pelicula? = null
                //Hacer mejor esto.
                viewModelScope.launch {
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
                                    _uiState.update {
                                        peli = result.data!!
                                        peli?.cast = cast
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
                        _uiState.update {
                            it.copy(
                                error = Constantes.NO_ADD_PELI
                            )
                        }
                    }
                }
            }
            is BuscarPelisContract.BuscarPelisEvent.BuscarPeliculas -> {
                viewModelScope.launch {
                    buscarPeliculasUC.invoke(event.nombrePeli)
                        .catch(action = { cause -> _uiState.update { it.copy(error = cause.message) } })
                        .collect { result ->
                            when (result) {
                                is ResultadoLlamada.Error ->
                                    _uiState.update { it.copy(error = result.message) }
                                is ResultadoLlamada.Loading ->
                                    _uiState.update { it.copy(isLoading = true) }
                                is ResultadoLlamada.Success -> {
                                    _uiState.update {
                                        it.copy(
                                            pelis = result.data!!,
                                            isLoading = false
                                        )
                                    }
                                }
                            }
                        }
                }
            }
            BuscarPelisContract.BuscarPelisEvent.ErrorMostrado -> {
                _uiState.update { it.copy(error = null) }
            }
        }
    }

    //Too lo del multiselect
    fun estaSeleccionado(genericoSeriesPelis: GenericoSeriesPelis): Boolean {
        return itemSeleccionados.contains(genericoSeriesPelis)
    }

    fun addItemMultiselect(genericoSeriesPelis: GenericoSeriesPelis) {
        if (itemSeleccionados.contains(genericoSeriesPelis)) {
            itemSeleccionados.remove(genericoSeriesPelis)
        } else {
            itemSeleccionados.add(genericoSeriesPelis)
        }
    }

    fun salirMultiSelect() {
        itemSeleccionados.clear()
    }

    fun mandarSeleccionadosFavoritos()
            : List<GenericoSeriesPelis> {
        return itemSeleccionados
    }
}