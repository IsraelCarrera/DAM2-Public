package com.example.appcompose.usescases

import com.example.appcompose.data.repositorio.GatosRepository
import javax.inject.Inject

class GetOneGato @Inject constructor(private val gatos: GatosRepository) {
    fun invoke(id: Int) = gatos.getOneGatito(id)
}