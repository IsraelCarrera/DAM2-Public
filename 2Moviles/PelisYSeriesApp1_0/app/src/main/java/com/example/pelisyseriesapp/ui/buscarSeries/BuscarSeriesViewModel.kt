package com.example.pelisyseriesapp.ui.buscarSeries

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pelisyseriesapp.data.utils.ResultadoLlamada
import com.example.pelisyseriesapp.domain.GenericoSeriesPelis
import com.example.pelisyseriesapp.domain.Serie
import com.example.pelisyseriesapp.usescases.series.*
import com.example.pelisyseriesapp.utils.Constantes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BuscarSeriesViewModel @Inject constructor(
    private val buscarSeriesUC: BuscarSeriesUC,
    private val addSerieRoomUC: AddSerieRoomUC,
    private val buscarSeriePorIdUC: BuscarSeriePorIdUC,
    private val buscarCastSerieUC: GetCastSerieUC,
    private val getEpisodiosTemporadaUC: GetEpisodiosTemporadaUC
) : ViewModel() {

    private val listaGenerica = mutableListOf<GenericoSeriesPelis>()

    private val _genericos = MutableLiveData<List<GenericoSeriesPelis>>()
    val genericos: LiveData<List<GenericoSeriesPelis>> get() = _genericos

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    //Para el multiselección
    private var itemSeleccionados = mutableListOf<GenericoSeriesPelis>()

    fun buscarSeries(nombreSerie: String) {
        viewModelScope.launch {
            when (val resultado = buscarSeriesUC.invoke(nombreSerie)) {
                is ResultadoLlamada.Error -> _error.value = resultado.message ?: ""
                is ResultadoLlamada.Success -> {
                    listaGenerica.clear()
                    resultado.data?.let { listaGenerica.addAll(it) }
                }
            }
            _genericos.value = listaGenerica.toList()
        }
    }

    //Creo que esto se podría meter mucho mejor en el Repositorio, pero sinceramente no sé como hacerlo bien y no tengo tiempo para ponerme a pelearme con ello.
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
                                val buscarEpisodios =
                                    getEpisodiosTemporadaUC.invoke(idSerie, it.numeroTemporada)
                                when (buscarEpisodios) {
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
                                } catch (e: Exception) {
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