package com.example.roomcrudmarvel.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.time.LocalDate

@Parcelize
data class Personaje(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val modificado: LocalDate,
    val linkFoto: String,
    val comics: List<Comic>?
) : Parcelable