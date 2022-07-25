package com.example.pelisyseriesapp.data.modelo.room.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "TEMPORADA", primaryKeys = ["idSerie", "numeroTemporada"])
data class TemporadaEntidad(
    @ColumnInfo(name = "idSerie")
    val idSerie: Int,
    @ColumnInfo(name = "numeroTemporada")
    val numTemporada: Int,
    @ColumnInfo(name = "numeroEpisodios")
    val numeroEpisodios: Int?,
    @ColumnInfo(name = "fechaEmision")
    val fechaEmision: String?,
    @ColumnInfo(name = "nombre")
    val nombre: String?,
    @ColumnInfo(name = "sipnosis")
    val sipnosis: String?,
    @ColumnInfo(name = "poster")
    val poster: String?,
    @ColumnInfo(name = "haSidoVista")
    val haSidoVista: Int
)