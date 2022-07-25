package com.example.roomcrudmarvel.data

import com.example.roomcrudmarvel.data.modelo.ComicEntidad
import com.example.roomcrudmarvel.data.modelo.PersonajeConComics
import com.example.roomcrudmarvel.data.modelo.PersonajeEntidad
import com.example.roomcrudmarvel.domain.Comic
import com.example.roomcrudmarvel.domain.Personaje

//Primero de la BBDD /(Entidades y relaciones con tablas) a los domain. Como no paso un personajeEntidad nunca, pues no se hizo.
fun ComicEntidad.toComic(): Comic {
    return Comic(this.idComic, this.idPersonaje, this.nombre)
}

fun PersonajeConComics.toPersonaje(): Personaje {
    return Personaje(
        this.heroe.id,
        this.heroe.nombre,
        this.heroe.descripcion,
        this.heroe.fechaModificado,
        this.heroe.linkFoto,
        this.comics?.map { it.toComic() })
}

// Ahora al reves, de los modelos del domain, a los del a BBDD.

fun Personaje.toPersonajeEntidad(): PersonajeEntidad {
    return PersonajeEntidad(this.id, this.nombre, this.descripcion, this.modificado, this.linkFoto)
}

fun Comic.toComicEntidad(): ComicEntidad {
    return ComicEntidad(this.idComic, this.idPersonaje, this.nombre)
}

fun Personaje.toPersonajeConComic(): PersonajeConComics {
    return PersonajeConComics(this.toPersonajeEntidad(), this.comics?.map { it.toComicEntidad() })
}