package com.example.pelisyseriesapp.data.modelo.room.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PELICULACACHE")
data class PeliculaCacheEntidad (
    @ColumnInfo(name = "idPelicula")
    @PrimaryKey
    val idPelicula: Int,
    @ColumnInfo(name = "titulo")
    val titulo: String,
    @ColumnInfo(name = "poster")
    val poster: String,
)