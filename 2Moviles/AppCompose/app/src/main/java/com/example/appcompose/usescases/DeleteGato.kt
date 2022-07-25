package com.example.appcompose.usescases

import com.example.appcompose.data.repositorio.GatosRepository
import javax.inject.Inject

class DeleteGato @Inject constructor(private val gatos: GatosRepository) {
    suspend fun invoke(id: Int) = gatos.deleteGato(id)
}