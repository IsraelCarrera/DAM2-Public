package com.example.pelisyseriesapp.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Temporada(
    val id: Int,
    val nombre: String?,
    val sipnopsis: String?,
    val poster: String?,
    val numeroTemporada: Int,
    val numEpisodios: Int?,
    var episodios: List<Episodio>?,
    val fechaEstreno: String?,
    var haSidoVista: Boolean
) : Parcelable