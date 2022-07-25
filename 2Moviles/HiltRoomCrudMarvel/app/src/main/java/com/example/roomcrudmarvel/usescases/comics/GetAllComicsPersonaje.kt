package com.example.roomcrudmarvel.usescases.comics

import com.example.roomcrudmarvel.data.PersonajeRepository
import com.example.roomcrudmarvel.data.toComic
import javax.inject.Inject

class GetAllComicsPersonaje @Inject constructor(val personajes: PersonajeRepository) {
    suspend fun invoke(idPersonaje: Int) =
        personajes.getComicsPersonaje(idPersonaje).map { it.toComic() }
}