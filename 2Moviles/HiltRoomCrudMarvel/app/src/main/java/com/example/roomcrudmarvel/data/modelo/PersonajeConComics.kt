package com.example.roomcrudmarvel.data.modelo

import androidx.room.Embedded
import androidx.room.Relation

data class PersonajeConComics(
    @Embedded val heroe: PersonajeEntidad,
    @Relation(
        parentColumn = "ID",
        entityColumn = "IDPersonaje"
    )
    val comics: List<ComicEntidad>?
)