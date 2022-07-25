package com.example.appcompose.usescases

import com.example.appcompose.data.repositorio.GatosRepository
import com.example.appcompose.domain.Gatos
import javax.inject.Inject

class AddGato @Inject constructor(private val gatos: GatosRepository) {
    suspend fun invoke(cat: Gatos) = gatos.addGatito(cat)
}