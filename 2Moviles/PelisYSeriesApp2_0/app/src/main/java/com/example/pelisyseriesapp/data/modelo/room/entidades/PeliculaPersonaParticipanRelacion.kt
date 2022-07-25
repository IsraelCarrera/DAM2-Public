package com.example.pelisyseriesapp.data.modelo.room.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "PELICULA_PERSONA_RELACION", primaryKeys = ["idPelicula", "idPersona"])
data class PeliculaPersonaParticipanRelacion(

    @ColumnInfo(name = "idPelicula")
    val idPelicula: Int,

    @ColumnInfo(name = "idPersona")
    val idPersona: Int
)