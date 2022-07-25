package com.example.pelisyseriesapp.data.modelo.room

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.pelisyseriesapp.data.modelo.room.entidades.PeliculaEntidad
import com.example.pelisyseriesapp.data.modelo.room.entidades.PeliculaPersonaParticipanRelacion
import com.example.pelisyseriesapp.data.modelo.room.entidades.PersonaEntidad


data class PeliculaRoom(
    @Embedded
    val pelicula: PeliculaEntidad,
    @Relation(
        parentColumn = "idPelicula",
        entityColumn = "idPersona",
        associateBy = Junction(PeliculaPersonaParticipanRelacion::class)
    )
    val casting: List<PersonaEntidad>?
)
