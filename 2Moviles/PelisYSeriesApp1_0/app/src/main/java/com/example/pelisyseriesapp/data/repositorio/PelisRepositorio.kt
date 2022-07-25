package com.example.pelisyseriesapp.data.repositorio

import com.example.pelisyseriesapp.data.sources.local.LocalPeliDataSource
import com.example.pelisyseriesapp.data.sources.remote.RemotePeliDataSource
import com.example.pelisyseriesapp.domain.Pelicula
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ActivityRetainedScoped
class PelisRepositorio @Inject constructor(
    private val remotePeliDataSource: RemotePeliDataSource,
    private val localPeliDataSource: LocalPeliDataSource
) {
    //Retrofit
    suspend fun getUnaPeli(id: Int) =
        withContext(Dispatchers.IO)
        {
            remotePeliDataSource.getOnePeli(id)
        }

    suspend fun buscarPeliculas(nombrePelicula: String) = withContext(Dispatchers.IO) {
        remotePeliDataSource.buscarPeliculas(nombrePelicula)
    }

    suspend fun buscarCastPeli(id: Int) = withContext(Dispatchers.IO) {
        remotePeliDataSource.buscarCastPeli(id)
    }

    //En este hay que comparar si es favorita o no, por ejemplo.

    //ROOM
    suspend fun addPelicula(pelicula: Pelicula) = withContext(Dispatchers.IO) {
        localPeliDataSource.addPelicula(pelicula)
    }

    suspend fun getAllPelisRoom() = withContext(Dispatchers.IO) {
        localPeliDataSource.getAllPeliculas()
    }

    suspend fun getOnePeliculaRoom(id: Int) =
        withContext(Dispatchers.IO) {
            localPeliDataSource.getOnePelicula(id)
        }

    suspend fun updatePelicula(pelicula: Pelicula) = withContext(Dispatchers.IO) {
        localPeliDataSource.updatePelicula(pelicula)
    }

    suspend fun deletePelicula(pelicula: Pelicula) = withContext(Dispatchers.IO) {
        localPeliDataSource.deletePelicula(pelicula)
    }
}