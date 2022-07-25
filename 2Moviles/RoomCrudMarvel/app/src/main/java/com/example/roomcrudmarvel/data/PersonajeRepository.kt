package com.example.roomcrudmarvel.data

import com.example.roomcrudmarvel.data.modelo.PersonajeConComics
import com.example.roomcrudmarvel.data.modelo.PersonajeEntidad

class PersonajeRepository(private val dao: PersonajeDao) {
    suspend fun getAllPersonajes() = dao.getAllPersonajes()
    suspend fun updatePersonaje(personaje: PersonajeEntidad) = dao.updatePersonaje(personaje)
    suspend fun addPersonaje(personaje: PersonajeConComics) = dao.addPersonaje(personaje)
    suspend fun deletePersonaje(personaje: PersonajeConComics) =
        dao.delete(personaje.heroe, personaje.comics)


}