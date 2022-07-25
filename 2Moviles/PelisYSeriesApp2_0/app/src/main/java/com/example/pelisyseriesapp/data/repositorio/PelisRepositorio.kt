package com.example.pelisyseriesapp.data.repositorio

import com.example.pelisyseriesapp.data.sources.local.LocalPeliDataSource
import com.example.pelisyseriesapp.data.sources.remote.RemotePeliDataSource
import com.example.pelisyseriesapp.data.utils.Constantes
import com.example.pelisyseriesapp.data.utils.ResultadoLlamada
import com.example.pelisyseriesapp.domain.ActoresActuan
import com.example.pelisyseriesapp.domain.Favoritos
import com.example.pelisyseriesapp.domain.GenericoSeriesPelis
import com.example.pelisyseriesapp.domain.Pelicula
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

import javax.inject.Inject

@ActivityRetainedScoped
class PelisRepositorio @Inject constructor(
    private val remotePeliDataSource: RemotePeliDataSource,
    private val localPeliDataSource: LocalPeliDataSource,
) {

    fun getUnaPeli(id: Int): Flow<ResultadoLlamada<Pelicula>> {
        return flow {
            emit(ResultadoLlamada.Loading())
            emit(remotePeliDataSource.getOnePeli(id))
        }.flowOn(Dispatchers.IO)
    }

    fun buscarPeliculas(nombrePelicula: String): Flow<ResultadoLlamada<List<GenericoSeriesPelis>>> {
        return flow {
            emit(ResultadoLlamada.Loading())
            //Emito el cacheo.
            emit(localPeliDataSource.getPelisCache())
            //Si nombrePelicula no está vacio hago esto y lo guardo en el cacheo
            if (nombrePelicula != Constantes.VACIO) {
                val pelisBuscadas = remotePeliDataSource.buscarPeliculas(nombrePelicula)
                if (pelisBuscadas is ResultadoLlamada.Success) {
                    pelisBuscadas.data?.let {
                        //Borro toda la anterior y pongo lo nuevo
                        localPeliDataSource.todoPeliCache(it)
                    }
                    //emito
                    emit(pelisBuscadas)
                }
            }
        }.flowOn(Dispatchers.IO)
    }


    fun buscarCastPeli(id: Int): Flow<ResultadoLlamada<List<ActoresActuan>>> {
        return flow {
            emit(ResultadoLlamada.Loading())
            emit(remotePeliDataSource.buscarCastPeli(id))
        }.flowOn(Dispatchers.IO)
    }


    //ROOM
    suspend fun addPelicula(pelicula: Pelicula) = withContext(Dispatchers.IO) {
        localPeliDataSource.addPelicula(pelicula)
    }


    fun getAllPelisRoom(): Flow<List<Favoritos>> {
        return localPeliDataSource.getAllPeliculas().flowOn(Dispatchers.IO)
    }

    fun getOnePeliculaRoom(id: Int): Flow<List<Pelicula>> {
        return localPeliDataSource.getOnePelicula(id).flowOn(Dispatchers.IO)
    }

    suspend fun updatePelicula(pelicula: Pelicula) = withContext(Dispatchers.IO) {
        localPeliDataSource.updatePelicula(pelicula)
    }

    suspend fun deletePelicula(idPelicula: Int) = withContext(Dispatchers.IO) {
        localPeliDataSource.deletePelicula(idPelicula)
    }
}