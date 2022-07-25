package com.example.appcompose.domain

import com.example.appcompose.ui.utils.Constantes

data class Gatos(
    val id: Int? = null,
    val nombre: String,
    val apellidos: String,
    val edad: Int,
    val dueno: String,
    var foto: String? = Constantes.IMAGEN_GATO,
)
