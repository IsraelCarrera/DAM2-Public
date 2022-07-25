package com.example.pelisyseriesapp.usescases.pelis

import com.example.pelisyseriesapp.data.repositorio.PelisRepositorio
import com.example.pelisyseriesapp.domain.Pelicula
import javax.inject.Inject

class UpdatePeliculaUC @Inject constructor(private val peliculas: PelisRepositorio) {
    suspend fun invoke(pelicula: Pelicula) = peliculas.updatePelicula(pelicula)
}