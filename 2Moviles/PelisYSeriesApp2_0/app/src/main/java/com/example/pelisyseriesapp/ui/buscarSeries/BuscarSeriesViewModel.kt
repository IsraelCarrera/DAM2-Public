package com.example.pelisyseriesapp.ui.buscarSeries

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pelisyseriesapp.data.utils.ResultadoLlamada
import com.example.pelisyseriesapp.domain.ActoresActuan
import com.example.pelisyseriesapp.domain.GenericoSeriesPelis
import com.example.pelisyseriesapp.domain.Serie
import com.example.pelisyseriesapp.usescases.series.*
import com.example.pelisyseriesapp.utils.Constantes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BuscarSeriesViewModel @Inject constructor(
    private val buscarSeriesUC: BuscarSeriesUC,
    private val addSerieRoomUC: AddSerieRoomUC,
    private val buscarSeriePorIdUC: BuscarSeriePorIdUC,
    private val buscarCastSerieUC: GetCastSerieUC,
    private val getEpisodiosTemporadaUC: GetEpisodiosTemporadaUC,
) : ViewModel() {

    private val _uiState: MutableStateFlow<BuscarSeriesContract.BuscarSeriesState> by lazy {
        MutableStateFlow(BuscarSeriesContract.BuscarSeriesState())
    }
    val uiState: StateFlow<BuscarSeriesContract.BuscarSeriesState> = _uiState

    //Para el multiselección
    private var itemSeleccionados = mutableListOf<GenericoSeriesPelis>()


    fun handleEvent(event: BuscarSeriesContract.BuscarSeriesEvent) {
        when (event) {
            is BuscarSeriesContract.BuscarSeriesEvent.AddSerieFavorita -> {
                var serie: Serie? = null
                var cast: List<ActoresActuan> = emptyList()
                viewModelScope.launch {
                    buscarSeriePorIdUC.invoke(event.idSerie)
                        .catch(action = { cause -> _uiState.update { it.copy(error = cause.message) } })
                        .collect { result ->
                            when (result) {
                                is ResultadoLlamada.Error ->
                                    _uiState.update { it.copy(error = result.message) }
                                is ResultadoLlamada.Loading ->
                                    _uiState.update { it.copy(isLoading = true) }
                                is ResultadoLlamada.Success -> {
                                    _uiState.update {
                                        serie = result.data!!
                                        it.copy(
                                            isLoading = false
                                        )
                                    }
                                }
                            }
                        }
                    buscarCastSerieUC.invoke(event.idSerie)
                        .catch(action = { cause -> _uiState.update { it.copy(error = cause.message) } })
                        .collect { result ->
                            when (result) {
                                is ResultadoLlamada.Error ->
                                    _uiState.update { it.copy(error = result.message) }
                                is ResultadoLlamada.Loading ->
                                    _uiState.update { it.copy(isLoading = true) }
                                is ResultadoLlamada.Success -> {
                                    cast = result.data!!
                                    _uiState.update {
                                        it.copy(
                                            isLoading = false
                                        )
                                    }
                                }
                            }
                        }
                    if (serie != null) {
                        serie?.cast = cast
                        serie?.temporadas?.map { temp ->
                            getEpisodiosTemporadaUC.invoke(event.idSerie, temp.numeroTemporada)
                                .catch(action = { cause -> _uiState.update { it.copy(error = cause.message) } })
                                .collect { result ->
                                    when (result) {
                                        is ResultadoLlamada.Error ->
                                            _uiState.update { it.copy(error = result.message) }
                                        is ResultadoLlamada.Loading ->
                                            _uiState.update { it.copy(isLoading = true) }
                                        is ResultadoLlamada.Success -> {
                                            temp.episodios = result.data
                                            _uiState.update {
                                                it.copy(
                                                    isLoading = false
                                                )
                                            }
                                        }
                                    }
                                }
                        }
                    }
                    //Ya está construida la serie, así pues, la añadimos a room
                    if (serie != null) {
                        addSerieRoomUC.invoke(serie!!)
                    } else {
                        _uiState.update {
                            it.copy(error = Constantes.NO_ADD_SERIE)
                        }

                    }
                }
            }
            is BuscarSeriesContract.BuscarSeriesEvent.BuscarSeries -> {
                viewModelScope.launch {
                    buscarSeriesUC.invoke(event.nombreSerie)
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
                                            series = result.data!!,
                                            isLoading = false
                                        )
                                    }
                                }
                            }
                        }
                }
            }
            BuscarSeriesContract.BuscarSeriesEvent.ErrorMostrado -> {
                _uiState.update { it.copy(error = null) }
            }
        }
    }


    //too lo del multiSelect
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

    fun mandarSeleccionadosFavoritos(): List<GenericoSeriesPelis> {
        return itemSeleccionados
    }
}