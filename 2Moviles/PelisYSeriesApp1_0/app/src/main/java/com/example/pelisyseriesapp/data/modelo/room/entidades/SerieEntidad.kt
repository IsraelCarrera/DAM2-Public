package com.example.pelisyseriesapp.data.modelo.room.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SERIE")
data class SerieEntidad(
    @ColumnInfo(name = "idSerie")
    @PrimaryKey
    val idSerie: Int,
    @ColumnInfo(name = "titulo")
    val titulo: String?,
    @ColumnInfo(name = "tituloOriginal")
    val tituloOriginal: String?,
    @ColumnInfo(name = "fechaEstreno")
    val fechaEstreno: String?,
    @ColumnInfo(name = "fechaFinal")
    val fechaFinal: String?,
    @ColumnInfo(name = "poster")
    val poster: String?,
    @ColumnInfo(name = "sipnosis")
    val sipnosis: String?,
    @ColumnInfo(name = "lenguajeOriginal")
    val lenguajeOriginal: String?,
    @ColumnInfo(name = "haSidoVista")
    val haSidoVista: Int
)