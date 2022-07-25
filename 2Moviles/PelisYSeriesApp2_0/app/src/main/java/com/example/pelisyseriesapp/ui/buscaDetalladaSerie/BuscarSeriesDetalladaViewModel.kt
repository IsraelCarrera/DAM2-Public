package com.example.pelisyseriesapp.ui.buscaDetalladaSerie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pelisyseriesapp.data.utils.ResultadoLlamada
import com.example.pelisyseriesapp.domain.ActoresActuan
import com.example.pelisyseriesapp.domain.Serie
import com.example.pelisyseriesapp.usescases.series.*
import com.example.pelisyseriesapp.utils.Constantes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BuscarSeriesDetalladaViewModel @Inject constructor(
    private val buscarSeriePorIdUC: BuscarSeriePorIdUC,
    private val buscarCastSerieUC: GetCastSerieUC,
    private val getOneSerieRoomUC: GetOneSerieRoomUC,
    private val addSerieRoomUC: AddSerieRoomUC,
    private val getEpisodiosTemporadaUC: GetEpisodiosTemporadaUC,
    private val updateSerieUC: UpdateSerieUC,
) : ViewModel() {

    private val _uiState: MutableStateFlow<DetalladaSerieContract.DetalladaSeriesState> by lazy {
        MutableStateFlow(DetalladaSerieContract.DetalladaSeriesState())
    }
    val uiState: StateFlow<DetalladaSerieContract.DetalladaSeriesState> = _uiState

    fun handleEvent(event: DetalladaSerieContract.DetalladaSeriesEvent) {
        when (event) {
            is DetalladaSerieContract.DetalladaSeriesEvent.AddSerieFavorita -> {
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
            is DetalladaSerieContract.DetalladaSeriesEvent.BuscarSerie -> {
                var serie: Serie? = null
                var serieRoom: Serie? = null
                var estaEnRoom = false
                viewModelScope.launch {
                    getOneSerieRoomUC.invoke(event.idSerie)
                        .catch(action = { cause -> _uiState.update { it.copy(error = cause.message) } })
                        .collect { result ->
                            try {
                                if (result.isNotEmpty()) {
                                    serieRoom = result[0]
                                    serieRoom?.temporadas?.map {
                                        it.episodios = emptyList()
                                    }
                                    estaEnRoom = true
                                }
                            } catch (e: Exception) {
                                _uiState.update { it.copy(error = e.message ?: Constantes.VACIO) }
                            }
                        }
                }
                if (!estaEnRoom) {
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
                        serie?.cast = cast
                        //Idem qeu en pelis, meto aquí este comparador, porque esta llamada tarda más y sino, me peta.
                        if (serieRoom != null) {
                            _uiState.update {
                                it.copy(
                                    serie = serieRoom!!
                                )
                            }
                        } else {
                            _uiState.update {
                                it.copy(
                                    serie = serie!!
                                )
                            }
                        }
                    }
                }
            }
            is DetalladaSerieContract.DetalladaSeriesEvent.UpdateSerie -> {
                viewModelScope.launch {
                    try {
                        updateSerieUC.invoke(event.serie)
                    } catch (e: Exception) {
                        _uiState.update { it.copy(error = e.message ?: Constantes.VACIO) }
                    }
                }
            }
            DetalladaSerieContract.DetalladaSeriesEvent.ErrorMostrado -> {
                _uiState.update { it.copy(error = null) }
            }
        }
    }
}