package com.example.examen2ev.domain

data class Pokemon (
    val id: Int = 0,
    val nombre: String,
    val generacion: String,
    val habilidades: List<String> = emptyList(),
    val caracteristicasInutiles: String,
    )