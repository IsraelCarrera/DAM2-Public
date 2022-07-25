package com.example.roomcrudmarvel.data

import com.example.roomcrudmarvel.data.modelo.PersonajeConComics
import com.example.roomcrudmarvel.data.modelo.PersonajeEntidad
import javax.inject.Inject

class PersonajeRepository @Inject constructor(private val dao: PersonajeDao) {
    suspend fun getAllPersonajes() = dao.getAllPersonajes()
    suspend fun updatePersonaje(personaje: PersonajeEntidad) = dao.updatePersonaje(personaje)
    suspend fun addPersonaje(personaje: PersonajeConComics) = dao.addPersonaje(personaje)
    suspend fun deletePersonaje(personaje: PersonajeConComics) =
        dao.delete(personaje.heroe, personaje.comics)
    suspend fun getComicsPersonaje(idPersonaje: Int) = dao.getComicsPersonaje(idPersonaje)
    suspend fun getPersonajesOrdenadosNombre() = dao.getPersonajesOrdenNombre()
    suspend fun getPersonajesOrdenadosId() = dao.getPersonajesOrdenId()
}