package com.example.pelisyseriesapp.ui.buscaDetalladaPeli

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pelisyseriesapp.data.utils.ResultadoLlamada
import com.example.pelisyseriesapp.domain.Pelicula
import com.example.pelisyseriesapp.usescases.pelis.*
import com.example.pelisyseriesapp.utils.Constantes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BuscarPelisDetalladaViewModel @Inject constructor(
    private val buscarPeliPorIdUC: BuscarPeliPorIdUC,
    private val buscarCastPeliUC: BuscarCastPeliUC,
    private val getOnePeliculaRoomUC: GetOnePeliculaRoomUC,
    private val addPeliRoomUC: AddPeliRoomUC,
    private val updatePeliculaUC: UpdatePeliculaUC
) : ViewModel() {
    //Como solo es una película, no será una lista.
    private var peli: Pelicula? = null

    private val _pelicula = MutableLiveData<Pelicula>()
    val pelicula: LiveData<Pelicula> get() = _pelicula

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error


    fun buscarPeliPorId(idPeli: Int) {
        viewModelScope.launch {
            //Primero comprobamos si la tenemos en room. Eso indicaría que si es favorita.
            val pelicula: List<Pelicula> = getOnePeliculaRoomUC.invoke(idPeli)
            if (pelicula.isNotEmpty()) run {
                _pelicula.value = pelicula[0]
            } else {
                //Si no está en room, hacemos la llamada a internet.
                val resultado = buscarPeliPorIdUC.invoke(idPeli)
                when (resultado) {
                    is ResultadoLlamada.Error -> _error.value = resultado.message ?: ""
                    is ResultadoLlamada.Success -> {
                        peli = resultado.data
                        //Llamada para el cast.
                        val buscarActores = buscarCastPeliUC.invoke(idPeli)
                        when (buscarActores) {
                            is ResultadoLlamada.Error -> _error.value = resultado.message ?: ""
                            is ResultadoLlamada.Success -> {
                                peli?.cast = buscarActores.data!!
                            }
                        }
                    }
                }
                _pelicula.value = peli!!
            }
        }
    }

    fun addPelicula(idPeli: Int) {
        viewModelScope.launch {
            val peli: Pelicula?
            val resultado = buscarPeliPorIdUC.invoke(idPeli)
            when (resultado) {
                is ResultadoLlamada.Error -> _error.value = resultado.message ?: ""
                is ResultadoLlamada.Success -> {
                    peli = resultado.data
                    //Llamada para el cast.
                    val buscarActores = buscarCastPeliUC.invoke(idPeli)
                    when (buscarActores) {
                        is ResultadoLlamada.Error -> _error.value = resultado.message ?: ""
                        is ResultadoLlamada.Success -> {
                            peli?.cast = buscarActores.data!!
                            //Estando ya aquí. Teniendo los datos de la peli y su cast. la añadimos.
                            if (peli != null) {
                                try {
                                    addPeliRoomUC.invoke(peli)
                                } catch (e: Exception) {
                                    _error.value = e.toString()
                                }
                            } else {
                                _error.value = Constantes.NO_ADD_PELI
                            }
                        }
                    }
                }
            }
        }
    }

    fun updatePelicula(pelicula: Pelicula) {
        viewModelScope.launch {
            try {
                updatePeliculaUC.invoke(pelicula)
            } catch (e: Exception) {
                _error.value = e.toString()
            }
        }
    }
}