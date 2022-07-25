package com.example.pelisyseriesapp.ui.buscaDetalladaSerie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pelisyseriesapp.data.utils.ResultadoLlamada
import com.example.pelisyseriesapp.domain.Serie
import com.example.pelisyseriesapp.usescases.series.*
import com.example.pelisyseriesapp.utils.Constantes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BuscarSeriesDetalladaViewModel @Inject constructor(
    private val buscarSeriePorIdUC: BuscarSeriePorIdUC,
    private val buscarCastSerieUC: GetCastSerieUC,
    private val getOneSerieRoomUC: GetOneSerieRoomUC,
    private val addSerieRoomUC: AddSerieRoomUC,
    private val getEpisodiosTemporadaUC: GetEpisodiosTemporadaUC,
    private val updateSerieUC: UpdateSerieUC
) : ViewModel() {
    private var serieVar: Serie? = null

    private val _serie = MutableLiveData<Serie>()
    val serie: LiveData<Serie> get() = _serie

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error


    fun buscarSerieId(idSerie: Int) {
        viewModelScope.launch {
            //Si está en room metemos esta.
            val serieRoom: List<Serie> = getOneSerieRoomUC.invoke(idSerie)
            if (serieRoom.isNotEmpty()) run {
                serieVar = serieRoom[0]
            }
            //Esto es solo si no existe en room.
            else {
                val resultado = buscarSeriePorIdUC.invoke(idSerie)
                when (resultado) {
                    is ResultadoLlamada.Error -> _error.value = resultado.message ?: ""
                    is ResultadoLlamada.Success -> {
                        serieVar = resultado.data
                        //Hacer la llamada de los actores.
                        val buscarActores = buscarCastSerieUC.invoke(idSerie)
                        when (buscarActores) {
                            is ResultadoLlamada.Error -> _error.value = resultado.message ?: ""
                            is ResultadoLlamada.Success -> {
                                serieVar?.cast = buscarActores.data!!

                            }
                        }
                        //Hacer la llamada de las temporadas
                    }
                }
                //Hacer la llamada de las temporadas.
                serieVar?.temporadas?.map {
                    when (val buscarEpisodios =
                        getEpisodiosTemporadaUC.invoke(idSerie, it.numeroTemporada)) {
                        is ResultadoLlamada.Error -> _error.value =
                            buscarEpisodios.message ?: ""
                        is ResultadoLlamada.Success -> {
                            it.episodios = buscarEpisodios.data!!
                        }
                    }
                }
            }
            _serie.value = serieVar!!
        }
    }

    fun addSerieFavorita(idSerie: Int) {
        viewModelScope.launch {
            val serie: Serie?
            when (val resultado = buscarSeriePorIdUC.invoke(idSerie)) {
                is ResultadoLlamada.Error -> _error.value = resultado.message ?: ""
                is ResultadoLlamada.Success -> {
                    serie = resultado.data
                    when (val buscarActores = buscarCastSerieUC.invoke(idSerie)) {
                        is ResultadoLlamada.Error -> _error.value = buscarActores.message ?: ""
                        is ResultadoLlamada.Success -> {
                            serie?.cast = buscarActores.data!!
                            //Ahora vamos a buscar los episodios de las temporadas.
                            serie?.temporadas?.map {
                                when (val buscarEpisodios =
                                    getEpisodiosTemporadaUC.invoke(idSerie, it.numeroTemporada)) {
                                    is ResultadoLlamada.Error -> _error.value =
                                        buscarEpisodios.message ?: ""
                                    is ResultadoLlamada.Success -> {
                                        it.episodios = buscarEpisodios.data!!
                                    }
                                }
                            }
                            if (serie != null) {
                                try {
                                    addSerieRoomUC.invoke(serie)
                                } catch (e: java.lang.Exception) {
                                    _error.value = e.message
                                }
                            } else {
                                _error.value = Constantes.NO_ADD_SERIE
                            }
                        }
                    }
                }
            }
        }
    }

    fun updateSerie(serie: Serie) {
        viewModelScope.launch {
            try {
                updateSerieUC.invoke(serie)
            } catch (e: Exception) {
                _error.value = e.toString()
            }
        }
    }
}