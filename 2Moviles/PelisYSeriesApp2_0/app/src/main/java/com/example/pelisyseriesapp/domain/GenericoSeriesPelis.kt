package com.example.pelisyseriesapp.domain


//Esta clase es la que usaré en los RV de buscar pelis y series, y además,
// la general donde se verá toda la info del las pelis y series guardadas.
data class GenericoSeriesPelis(
    val id: Int,
    val titulo: String?,
    val poster: String?,
    val esFavorito: Boolean
)