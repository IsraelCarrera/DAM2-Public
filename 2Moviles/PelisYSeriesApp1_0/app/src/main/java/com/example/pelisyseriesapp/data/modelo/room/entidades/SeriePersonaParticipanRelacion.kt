package com.example.pelisyseriesapp.data.modelo.room.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "SERIE_PERSONA_RELACION", primaryKeys = ["idSerie", "idPersona"])
data class SeriePersonaParticipanRelacion(
    @ColumnInfo(name = "idSerie")
    val idSerie: Int,
    @ColumnInfo(name = "idPersona")
    val idPersona: Int
)