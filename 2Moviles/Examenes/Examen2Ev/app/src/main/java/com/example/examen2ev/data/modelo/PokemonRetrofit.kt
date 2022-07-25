package com.example.examen2ev.data.modelo

import com.google.gson.annotations.SerializedName

data class PokemonRetrofit(
    @SerializedName("nombre")
    val nombre: String,
    @SerializedName("generacion")
    val generacion: String,
    @SerializedName("habilidades")
    val habilidades: List<String>,
    @SerializedName("caracteristicasInutiles")
    val caracteristicasInutiles: String,
    @SerializedName("id")
    var id: Int = 0
)

