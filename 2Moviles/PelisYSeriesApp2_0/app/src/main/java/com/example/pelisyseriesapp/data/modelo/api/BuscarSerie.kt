package com.example.pelisyseriesapp.data.modelo.api


import com.google.gson.annotations.SerializedName

data class BuscarSerie(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val resultadoBuscarSeries: List<ResultadoBuscarSerie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)