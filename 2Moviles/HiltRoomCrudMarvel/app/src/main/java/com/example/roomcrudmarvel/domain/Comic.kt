package com.example.roomcrudmarvel.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Comic(
    val idComic: Int,
    var idPersonaje: Int,
    val nombre: String
) : Parcelable
