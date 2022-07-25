package com.example.pelisyseriesapp.data.sources.local


import com.example.pelisyseriesapp.data.toFavoritos
import com.example.pelisyseriesapp.data.toPelicula
import com.example.pelisyseriesapp.data.toPeliculaEntidad
import com.example.pelisyseriesapp.data.toPeliculaRoom
import com.example.pelisyseriesapp.domain.Pelicula
import javax.inject.Inject

class LocalPeliDataSource @Inject constructor(private val dao: PeliculaLocalDao) {

    //Creo que hay que mapear aquí, al igual que con el local
    suspend fun addPelicula(pelicula: Pelicula) = dao.addPelicula(pelicula.toPeliculaRoom())
    suspend fun getAllPeliculas() = dao.getAllPeliculas().map { it.toFavoritos() }
    suspend fun getOnePelicula(id: Int) = dao.getOnePelicula(id).map { it.toPelicula() }
    suspend fun updatePelicula(pelicula: Pelicula) =
        dao.updatePelicula(pelicula.toPeliculaEntidad())

    suspend fun deletePelicula(pelicula: Pelicula) =
        dao.deletePelicula(pelicula.toPeliculaEntidad())
}