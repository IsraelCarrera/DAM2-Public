package com.example.appcompose.ui.addGato

import com.example.appcompose.domain.Gatos

interface AddGatoContract {
    sealed class AddGatoEvent {
        data class AddGato(val gato: Gatos?) : AddGatoEvent()
        object ErrorMostrado : AddGatoEvent()
        object MensajeMostrado : AddGatoEvent()
    }

    data class AddGatoState(
        val error: String? = null,
        val mensaje: String? = null,
    )
}