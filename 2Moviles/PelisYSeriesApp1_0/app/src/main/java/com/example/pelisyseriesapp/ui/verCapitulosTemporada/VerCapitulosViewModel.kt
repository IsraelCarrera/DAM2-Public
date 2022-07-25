package com.example.pelisyseriesapp.ui.verCapitulosTemporada

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pelisyseriesapp.domain.Episodio
import com.example.pelisyseriesapp.domain.Temporada
import com.example.pelisyseriesapp.usescases.series.UpdateEpisodioUC
import com.example.pelisyseriesapp.usescases.series.UpdateTemporadaUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerCapitulosViewModel @Inject constructor(
    private val updateEpisodioUC: UpdateEpisodioUC,
    private val updateTemporadaUC: UpdateTemporadaUC
) : ViewModel() {


    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error


    fun updateEpisodio(episodio: Episodio) {
        viewModelScope.launch {
            try {
                updateEpisodioUC.invoke(episodio)
            } catch (e: Exception) {
                _error.value = e.toString()
            }
        }
    }

    fun updateTemporada(temporada: Temporada) {
        viewModelScope.launch {
            try {
                updateTemporadaUC.invoke(temporada)
            } catch (e: Exception) {
                _error.value = e.toString()
            }
        }
    }
}