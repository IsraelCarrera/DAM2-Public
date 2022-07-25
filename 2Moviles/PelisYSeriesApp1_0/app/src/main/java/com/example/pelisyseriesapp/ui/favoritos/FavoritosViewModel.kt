package com.example.pelisyseriesapp.ui.favoritos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pelisyseriesapp.domain.Favoritos
import com.example.pelisyseriesapp.usescases.pelis.DeletePeliculaRoomUC
import com.example.pelisyseriesapp.usescases.pelis.GetAllPelisRoomUC
import com.example.pelisyseriesapp.usescases.pelis.GetOnePeliculaRoomUC
import com.example.pelisyseriesapp.usescases.series.DeleteSerieRoomUC
import com.example.pelisyseriesapp.usescases.series.GetAllSeriesRoomUC
import com.example.pelisyseriesapp.usescases.series.GetOneSerieRoomUC
import com.example.pelisyseriesapp.utils.Constantes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritosViewModel @Inject constructor(
    private val getAllPelisRoomUC: GetAllPelisRoomUC,
    private val getAllSeriesRoomUC: GetAllSeriesRoomUC,
    private val deletePeliculaRoomUC: DeletePeliculaRoomUC,
    private val getOnePeliculaRoomUC: GetOnePeliculaRoomUC,
    private val getOneSerieRoomUC: GetOneSerieRoomUC,
    private val deleteSerieRoomUC: DeleteSerieRoomUC
) : ViewModel() {


    private val listaFavoritos = mutableListOf<Favoritos>()
    val _favoritos = MutableLiveData<List<Favoritos>>()
    val favoritos: LiveData<List<Favoritos>> get() = _favoritos


    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _avisos = MutableLiveData<String>()
    val avisos: LiveData<String> get() = _avisos

    //GetAll
    fun getAllFavoritos() {
        viewModelScope.launch {
            try {
                listaFavoritos.clear()
                getAllPelisRoomUC.invoke().let { listaFavoritos.addAll(it) }
                getAllSeriesRoomUC.invoke().let {
                    listaFavoritos.addAll(it)
                }
            } catch (e: Exception) {
                _error.value = e.message
            }
            _favoritos.value = listaFavoritos.toList()
        }
    }


    //Borrar Pelicula
    fun borrarPelicula(id: Int) {
        viewModelScope.launch {
            try {
                deletePeliculaRoomUC.invoke(getOnePeliculaRoomUC.invoke(id)[0])
                _avisos.value = Constantes.PELI_ELIMINADA_EXITO
                getAllFavoritos()
            } catch (e: Exception) {
                _error.value = e.message
            }

        }
    }

    //Borrar serie
    fun borrarSerie(id: Int) {
        viewModelScope.launch {
            try {
                deleteSerieRoomUC.invoke(getOneSerieRoomUC.invoke(id)[0])
                _avisos.value = Constantes.SERIE_ELIMINADA_EXITO
                getAllFavoritos()
            } catch (e: Exception) {
                _error.value = e.message
            }

        }
    }


}