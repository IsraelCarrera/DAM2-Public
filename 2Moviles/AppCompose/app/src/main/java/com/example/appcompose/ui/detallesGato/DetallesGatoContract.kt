package com.example.appcompose.ui.detallesGato

import com.example.appcompose.domain.Gatos

interface DetallesGatoContract {
    sealed class DetallesGatoEvent {
        data class BuscarGatoPorId(val idGato: Int) : DetallesGatoEvent()
        object ErrorMostrado : DetallesGatoEvent()
    }

    data class DetallesGatoState(
        val gato: Gatos? = null,
        val error: String? = null,
    )
}