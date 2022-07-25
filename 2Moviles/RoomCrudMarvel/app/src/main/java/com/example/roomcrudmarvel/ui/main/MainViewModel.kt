package com.example.roomcrudmarvel.ui.main

import androidx.lifecycle.*
import com.example.roomcrudmarvel.domain.Personaje
import com.example.roomcrudmarvel.usescases.personajes.AddPersonaje
import com.example.roomcrudmarvel.usescases.personajes.DeletePersonaje
import com.example.roomcrudmarvel.usescases.personajes.GetAllPersonajes
import com.example.roomcrudmarvel.usescases.personajes.UpdatePersonaje
import kotlinx.coroutines.launch

//Meter todos los usescases (Lo que son servicios) juntos al principio.
class MainViewModel(
    private val getAll: GetAllPersonajes,
    private val updatePers: UpdatePersonaje,
    private val addPers: AddPersonaje,
    private val deletePers: DeletePersonaje
) : ViewModel() {


    //La lista de heroes/personajes que vamos a ir jugando.
    private val _personajes = MutableLiveData<List<Personaje>>()
    val personajes: LiveData<List<Personaje>> get() = _personajes


    fun getAllPersonajes() {
        viewModelScope.launch {
            _personajes.value = getAll.invoke()
        }
    }
    //Mi idea para coger un personaje. Pasar el ID. y hacer un getOnePersonaje, y devolver personaje.
    // No tengo ni P.I de como hacerlo.

    fun updatePersonaje(pers: Personaje) {
        viewModelScope.launch {
            updatePers.invoke(pers)
            getAllPersonajes()
        }
    }

    fun addPersonaje(pers: Personaje) {
        viewModelScope.launch {
            addPers.invoke(pers)
            getAllPersonajes()
        }
    }

    fun deletePersonaje(pers: Personaje) {
        viewModelScope.launch {
            deletePers.invoke(pers)
            getAllPersonajes()
        }
    }

}

//El factory para instanciar el ViewModel.
class MainViewModelFactory(
    private val getAll: GetAllPersonajes,
    private val updatePers: UpdatePersonaje,
    private val addPers: AddPersonaje,
    private val deletePers: DeletePersonaje
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(getAll, updatePers, addPers, deletePers) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}