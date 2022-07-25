package com.example.pelisyseriesapp.usescases.pelis

import com.example.pelisyseriesapp.data.repositorio.PelisRepositorio
import javax.inject.Inject

class BuscarPeliculasUC @Inject constructor(private val pelis: PelisRepositorio) {
    suspend fun invoke(nombrePelicula: String) = pelis.buscarPeliculas(nombrePelicula)
}