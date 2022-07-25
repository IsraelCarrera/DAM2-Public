package com.example.pelisyseriesapp.ui.verCapitulosTemporada

import com.example.pelisyseriesapp.domain.Episodio
import com.example.pelisyseriesapp.domain.Temporada

interface VerCapitulosTemporadaContract {
    sealed class VerCapitulosTemporadaEvent {
        data class GetEpisodios(val idSerie: Int, val numTemporada: Int) :
            VerCapitulosTemporadaEvent()

        data class UpdateEpisodio(val episodio: Episodio) : VerCapitulosTemporadaEvent()
        data class UpdateTemporada(val temporada: Temporada) : VerCapitulosTemporadaEvent()
        object ErrorMostrado : VerCapitulosTemporadaEvent()
    }

    data class VerCapitulosTemporadaState(
        val capitulos: List<Episodio> = emptyList(),
        val error: String? = null,
    )
}