package com.example.pelisyseriesapp.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Pelicula(
    val id: Int,
    val titulo: String?,
    val fechaEstreno: String?,
    val poster: String?,
    var cast: List<ActoresActuan>?,
    val tituloOriginal: String?,
    val sipnosis: String?,
    val lenguajeOriginal: String?,
    var haSidoVista: Boolean,
    val esFavorita: Boolean
) : Parcelable