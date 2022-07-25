package com.example.pelisyseriesapp.data.modelo.room


import androidx.room.Embedded
import androidx.room.Relation
import com.example.pelisyseriesapp.data.modelo.room.entidades.CapituloEntidad
import com.example.pelisyseriesapp.data.modelo.room.entidades.TemporadaEntidad


data class TemporadaWithCapitulos(
    @Embedded
    val temporada: TemporadaEntidad,
    @Relation(
        parentColumn = "numeroTemporada",
        entityColumn = "numeroTemporada"
    )
    val capitulos: List<CapituloEntidad>?
)