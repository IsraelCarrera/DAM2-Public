package com.example.pelisyseriesapp.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ActoresActuan(
    val id: Int,
    val nombrePersonaje: String?,
    val genero: String?,
    val nombreReal: String?,
    val foto: String?
) : Parcelable