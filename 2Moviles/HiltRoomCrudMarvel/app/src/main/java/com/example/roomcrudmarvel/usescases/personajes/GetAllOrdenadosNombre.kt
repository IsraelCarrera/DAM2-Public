package com.example.roomcrudmarvel.usescases.personajes

import com.example.roomcrudmarvel.data.PersonajeRepository
import com.example.roomcrudmarvel.data.toPersonaje
import javax.inject.Inject

class GetAllOrdenadosNombre @Inject constructor(val personajes: PersonajeRepository) {
    suspend fun invoke() = personajes.getPersonajesOrdenadosNombre().map { it.toPersonaje() }
}