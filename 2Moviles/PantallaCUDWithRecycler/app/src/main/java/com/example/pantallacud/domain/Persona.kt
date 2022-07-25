package com.example.pantallacud.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Persona( var id:Int,
                    var nombre: String,
                    var pass: String,
                    var tel: String,
                    var email: String,
                    var sexo: Boolean,
                    var suscribirse: Boolean,
                    var juegaEnPc: Boolean,
                    var juegaEnConsola: Boolean
                ) : Parcelable
