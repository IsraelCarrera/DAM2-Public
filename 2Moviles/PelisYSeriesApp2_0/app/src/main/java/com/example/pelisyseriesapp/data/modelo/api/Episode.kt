package com.example.pelisyseriesapp.data.modelo.api


import com.google.gson.annotations.SerializedName

data class Episode(
    @SerializedName("air_date")
    val airDate: String,
    @SerializedName("crew")
    val crew: List<CrewX>,
    @SerializedName("episode_number")
    val episodeNumber: Int,
    @SerializedName("guest_stars")
    val guestStars: List<Any>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("production_code")
    val productionCode: String,
    @SerializedName("season_number")
    val seasonNumber: Int,
    @SerializedName("still_path")
    val stillPath: Any,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int
)