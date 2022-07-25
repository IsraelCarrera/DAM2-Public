package com.example.pelisyseriesapp.data.modelo.room.entidades


import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "CAPITULO", primaryKeys = ["idSerie", "numeroTemporada", "numeroCapitulo"])
data class CapituloEntidad(

    @ColumnInfo(name = "idSerie")
    val idSerie: Int,
    @ColumnInfo(name = "numeroTemporada")
    val numeroTemporada: Int,
    @ColumnInfo(name = "numeroCapitulo")
    val numeroCapitulo: Int,
    @ColumnInfo(name = "fechaEmision")
    val fechaEmision: String?,
    @ColumnInfo(name = "nombre")
    val nombre: String?,
    @ColumnInfo(name = "sipnosis")
    val sipnosis: String?,
    @ColumnInfo(name = "haSidoVisto")
    val haSidoVisto: Int
)