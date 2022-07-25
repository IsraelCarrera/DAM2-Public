package com.example.pelisyseriesapp.usescases.pelis

import com.example.pelisyseriesapp.data.repositorio.PelisRepositorio
import com.example.pelisyseriesapp.domain.Pelicula
import javax.inject.Inject

class DeletePeliculaRoomUC @Inject constructor(private val pelicula: PelisRepositorio) {
    suspend fun invoke(peliculaDelete: Pelicula) = pelicula.deletePelicula(peliculaDelete)
}