package com.example.appcompose.ui.listarGatos

import com.example.appcompose.domain.GatosLista

interface ListarGatosContract {
    sealed class ListarGatosEvent {
        data class DeleteGato(val idGato: Int) : ListarGatosEvent()
        object BuscarGatos : ListarGatosEvent()
        object ErrorMostrado : ListarGatosEvent()
        object MensajeMostrado : ListarGatosEvent()
    }

    data class ListarGatosState(
        val gatos: List<GatosLista> = emptyList(),
        val error: String? = null,
        val mensaje: String? = null,
    )
}