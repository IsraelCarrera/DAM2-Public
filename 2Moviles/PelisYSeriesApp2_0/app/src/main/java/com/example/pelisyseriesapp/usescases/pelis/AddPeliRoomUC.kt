package com.example.pelisyseriesapp.usescases.pelis

import com.example.pelisyseriesapp.data.repositorio.PelisRepositorio
import com.example.pelisyseriesapp.domain.Pelicula
import javax.inject.Inject

class AddPeliRoomUC @Inject constructor(private val pelis: PelisRepositorio) {
    suspend fun invoke(pelicula: Pelicula) = pelis.addPelicula(pelicula)
}