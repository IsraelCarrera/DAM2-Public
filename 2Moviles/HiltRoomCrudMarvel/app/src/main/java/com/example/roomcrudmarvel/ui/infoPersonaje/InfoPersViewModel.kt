package com.example.roomcrudmarvel.ui.infoPersonaje

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomcrudmarvel.domain.Comic
import com.example.roomcrudmarvel.domain.Personaje
import com.example.roomcrudmarvel.usescases.comics.GetAllComicsPersonaje
import com.example.roomcrudmarvel.usescases.personajes.UpdatePersonaje
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InfoPersViewModel @Inject constructor(
    private val updatePers: UpdatePersonaje,
    private val getAllComics: GetAllComicsPersonaje
) : ViewModel() {

    //La lista de comics, para verlos
    private val _comics = MutableLiveData<List<Comic>>()
    val comics: LiveData<List<Comic>> get() = _comics

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun getAllComics(idPers: Int) {
        viewModelScope.launch {
            try {
                _comics.value = getAllComics.invoke(idPers)
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    //Update
    fun updatePersonaje(pers: Personaje) {
        viewModelScope.launch {
            try {
                updatePers.invoke(pers)
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}