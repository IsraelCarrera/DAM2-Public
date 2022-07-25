package com.example.pelisyseriesapp.data.modelo.api


import com.google.gson.annotations.SerializedName

data class InfoTemporada(
    @SerializedName("air_date")
    val airDate: String,
    @SerializedName("episodes")
    val episodes: List<Episode>,
    @SerializedName("_id")
    val idNoUsar: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: Any,
    @SerializedName("season_number")
    val seasonNumber: Int
)