package com.example.pelisyseriesapp.domain

data class Serie(
    val id: Int,
    val titulo: String?,
    val fechaEstreno: String?,
    val fechaFinal: String?,
    val poster: String?,
    var cast: List<ActoresActuan>?,
    val tituloOriginal: String?,
    val sipnosis: String?,
    val lenguajeOriginal: String?,
    val temporadas: List<Temporada>?,
    var haSidoVista: Boolean,
    val esFavorita: Boolean
)