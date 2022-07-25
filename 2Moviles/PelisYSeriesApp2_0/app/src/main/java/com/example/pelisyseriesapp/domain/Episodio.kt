package com.example.pelisyseriesapp.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Episodio(
    val id: Int,
    val numTemporada: Int,
    val numCapitulo: Int,
    val nombre: String?,
    val sipnosis: String?,
    val fechaEmision: String?,
    var haSidoVisto: Boolean
) : Parcelable