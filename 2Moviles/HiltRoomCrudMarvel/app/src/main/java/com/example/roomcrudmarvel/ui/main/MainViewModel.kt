package com.example.roomcrudmarvel.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomcrudmarvel.domain.Personaje
import com.example.roomcrudmarvel.usescases.personajes.DeletePersonaje
import com.example.roomcrudmarvel.usescases.personajes.GetAllOrdenadosId
import com.example.roomcrudmarvel.usescases.personajes.GetAllOrdenadosNombre
import com.example.roomcrudmarvel.usescases.personajes.GetAllPersonajes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

//Meter todos los usescases (Lo que son servicios) juntos al principio.
@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAll: GetAllPersonajes,
    private val deletePers: DeletePersonaje,
    private val ordenNombre: GetAllOrdenadosNombre,
    private val ordenId: GetAllOrdenadosId
) : ViewModel() {


    //La lista de heroes/personajes que vamos a ir jugando.
    private val _personajes = MutableLiveData<List<Personaje>>()
    val personajes: LiveData<List<Personaje>> get() = _personajes

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun getAllPersonajes() {
        viewModelScope.launch {
            try {
                _personajes.value = getAll.invoke()
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun deletePersonaje(pers: Personaje) {
        viewModelScope.launch {
            try {
                deletePers.invoke(pers)
                getAllPersonajes()
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun ordenarPorNombre() {
        viewModelScope.launch {
            try {
                _personajes.value = ordenNombre.invoke()
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun ordenarPorId() {
        viewModelScope.launch {
            try {
                _personajes.value = ordenId.invoke()
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}