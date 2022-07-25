package com.example.roomcrudmarvel.usescases.personajes

import com.example.roomcrudmarvel.data.PersonajeRepository
import com.example.roomcrudmarvel.data.toPersonaje

class GetAllPersonajes(val personajes: PersonajeRepository) {

    suspend fun invoke() = personajes.getAllPersonajes().map { it.toPersonaje() }
}