package com.example.pelisyseriesapp.ui.buscarPelis

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pelisyseriesapp.data.utils.ResultadoLlamada
import com.example.pelisyseriesapp.domain.GenericoSeriesPelis
import com.example.pelisyseriesapp.domain.Pelicula
import com.example.pelisyseriesapp.usescases.pelis.AddPeliRoomUC
import com.example.pelisyseriesapp.usescases.pelis.BuscarCastPeliUC
import com.example.pelisyseriesapp.usescases.pelis.BuscarPeliPorIdUC
import com.example.pelisyseriesapp.usescases.pelis.BuscarPeliculasUC
import com.example.pelisyseriesapp.utils.Constantes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BuscarPelisViewModel @Inject constructor(
    private val buscarPeliculasUC: BuscarPeliculasUC,
    private val addPeliRoomUC: AddPeliRoomUC,
    private val buscarPeliPorIdUC: BuscarPeliPorIdUC,
    private val buscarCastPeliUC: BuscarCastPeliUC
) : ViewModel() {

    private val listaGenerica = mutableListOf<GenericoSeriesPelis>()

    private val _genericos = MutableLiveData<List<GenericoSeriesPelis>>()
    val genericos: LiveData<List<GenericoSeriesPelis>> get() = _genericos

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    //Para el multiselección
    private var itemSeleccionados = mutableListOf<GenericoSeriesPelis>()

    fun buscarPeliculas(nombrePeli: String) {

        viewModelScope.launch {
            val resultado = buscarPeliculasUC.invoke(nombrePeli)
            when (resultado) {
                is ResultadoLlamada.Error -> _error.value = resultado.message ?: ""
                is ResultadoLlamada.Success -> {
                    listaGenerica.clear()
                    resultado.data?.let { listaGenerica.addAll(it) }
                }
            }
            _genericos.value = listaGenerica.toList()
        }
    }

    fun addPeliFavorito(idPeli: Int) {
        //Primero cogemos toda la info de la peli.
        viewModelScope.launch {
            val peli: Pelicula?
            when (val resultado = buscarPeliPorIdUC.invoke(idPeli)) {
                is ResultadoLlamada.Error -> _error.value = resultado.message ?: ""
                is ResultadoLlamada.Success -> {
                    peli = resultado.data
                    //Llamada para el cast.
                    when (val buscarActores = buscarCastPeliUC.invoke(idPeli)) {
                        is ResultadoLlamada.Error -> _error.value = resultado.message ?: ""
                        is ResultadoLlamada.Success -> {
                            peli?.cast = buscarActores.data!!
                            //Estando ya aquí. Teniendo los datos de la peli y su cast. la añadimos.
                            if (peli != null) {
                                addPeliRoomUC.invoke(peli)
                            } else {
                                _error.value = Constantes.NO_ADD_PELI
                            }
                        }
                    }
                }
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

    fun mandarSeleccionadosFavoritos(): List<GenericoSeriesPelis> {
        return itemSeleccionados
    }
}