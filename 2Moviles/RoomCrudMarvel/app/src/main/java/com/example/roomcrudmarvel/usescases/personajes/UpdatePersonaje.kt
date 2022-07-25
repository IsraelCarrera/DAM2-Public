package com.example.roomcrudmarvel.usescases.personajes

import com.example.roomcrudmarvel.data.PersonajeRepository
import com.example.roomcrudmarvel.data.toPersonajeEntidad
import com.example.roomcrudmarvel.domain.Personaje

class UpdatePersonaje(val personajes: PersonajeRepository) {

    suspend fun invoke(personaje: Personaje) =
        personajes.updatePersonaje(personaje.toPersonajeEntidad())
}