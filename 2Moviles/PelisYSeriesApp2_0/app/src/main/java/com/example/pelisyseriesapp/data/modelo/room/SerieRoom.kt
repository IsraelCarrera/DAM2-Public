package com.example.pelisyseriesapp.data.modelo.room

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.pelisyseriesapp.data.modelo.room.entidades.PersonaEntidad
import com.example.pelisyseriesapp.data.modelo.room.entidades.SeriePersonaParticipanRelacion

data class SerieRoom(
    @Embedded
    val serie: SerieWithTemporadas,
    @Relation(
        entity = PersonaEntidad::class,
        parentColumn = "idSerie",
        entityColumn = "idPersona",
        associateBy = Junction(SeriePersonaParticipanRelacion::class)
    )
    val casting: List<PersonaEntidad>?
)