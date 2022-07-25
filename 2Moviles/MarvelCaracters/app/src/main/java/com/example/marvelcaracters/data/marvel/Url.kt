package com.example.marvelcaracters.data.marvel


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Url(
    @Json(name = "type")
    val type: String,
    @Json(name = "url")
    val url: String
)