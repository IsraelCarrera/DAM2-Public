package com.example.roomcrudmarvel.usescases.personajes

import com.example.roomcrudmarvel.data.PersonajeRepository
import com.example.roomcrudmarvel.data.toPersonajeConComic
import com.example.roomcrudmarvel.domain.Personaje

class AddPersonaje(val personajes: PersonajeRepository) {
    suspend fun invoke(personaje: Personaje) =
        personajes.addPersonaje(personaje.toPersonajeConComic())
}