package com.example.marvelcaracters.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.time.LocalDateTime


@Parcelize
data class Personaje(
    val nombre: String,
    val descripcion: String,
    val id: Int,
    val modificado: LocalDateTime,
    val linkFoto: String,
    val extensionFoto: String,
    val comics: List<String>
) : Parcelable