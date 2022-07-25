package com.example.pelisyseriesapp.data.modelo.room

import androidx.room.Embedded
import androidx.room.Relation
import com.example.pelisyseriesapp.data.modelo.room.entidades.SerieEntidad
import com.example.pelisyseriesapp.data.modelo.room.entidades.TemporadaEntidad

data class SerieWithTemporadas(
    @Embedded
    val serie: SerieEntidad,
    @Relation(
        entity = TemporadaEntidad::class,
        parentColumn = "idSerie",
        entityColumn = "idSerie",
    )
    val temporadas: List<TemporadaWithCapitulos>?
)