package com.example.roomcrudmarvel.ui.crearPersonaje

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomcrudmarvel.domain.Comic
import com.example.roomcrudmarvel.domain.Personaje
import com.example.roomcrudmarvel.usescases.personajes.AddPersonaje
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CrearPersViewModel @Inject constructor(
    private val addPers: AddPersonaje
) : ViewModel() {

    //En este caso, será un viewModel para add comics. Estos luego se guardan junto al Personaje.
    private val listaComics: MutableList<Comic> = mutableListOf()
    private val _comics = MutableLiveData<List<Comic>>()
    val comics: LiveData<List<Comic>> get() = _comics

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error


    fun addComics(c: Comic) {
        listaComics.add(c)
        _comics.value = listaComics

    }

    fun addPersonaje(pers: Personaje) {
        viewModelScope.launch {
            try {
                addPers.invoke(pers)
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}