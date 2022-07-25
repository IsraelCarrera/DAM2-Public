package com.example.pelisyseriesapp.data.modelo.api


import com.google.gson.annotations.SerializedName

data class BuscarPeliculas(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<ResultadoBuscarPeliculas>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)