package com.example.pelisyseriesapp.data.modelo.room.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PELICULA")
data class PeliculaEntidad(

    @ColumnInfo(name = "idPelicula")
    @PrimaryKey
    val idPelicula: Int,
    @ColumnInfo(name = "titulo")
    val titulo: String?,
    @ColumnInfo(name = "tituloOriginal")
    val tituloOriginal: String?,
    @ColumnInfo(name = "poster")
    val poster: String?,
    @ColumnInfo(name = "fechaEstreno")
    val fechaEstreno: String?,
    @ColumnInfo(name = "sipnosis")
    val sipnosis: String?,
    @ColumnInfo(name = "lenguajeOriginal")
    val lenguajeOriginal: String?,
    @ColumnInfo(name = "haSidoVista")
    val haSidoVista: Int
)