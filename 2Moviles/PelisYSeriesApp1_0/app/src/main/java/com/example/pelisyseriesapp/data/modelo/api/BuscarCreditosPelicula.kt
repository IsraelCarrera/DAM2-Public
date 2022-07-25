package com.example.pelisyseriesapp.data.modelo.api


import com.google.gson.annotations.SerializedName

data class BuscarCreditosPelicula(
    @SerializedName("cast")
    val castPeli: List<CastPeli>,
    @SerializedName("crew")
    val crewPeli: List<Crew>,
    @SerializedName("id")
    val id: Int
)