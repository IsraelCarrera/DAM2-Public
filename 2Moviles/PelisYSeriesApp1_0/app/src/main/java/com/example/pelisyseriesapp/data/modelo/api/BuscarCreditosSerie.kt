package com.example.pelisyseriesapp.data.modelo.api


import com.google.gson.annotations.SerializedName

data class BuscarCreditosSerie(
    @SerializedName("cast")
    val cast: List<CastSerie>,
    @SerializedName("crew")
    val crew: List<Crew>,
    @SerializedName("id")
    val id: Int
)