package com.example.appcompose.usescases

import com.example.appcompose.data.repositorio.GatosRepository
import javax.inject.Inject

class ListGatos @Inject constructor(private val gatos: GatosRepository) {
    fun invoke() = gatos.getAllGatos()
}