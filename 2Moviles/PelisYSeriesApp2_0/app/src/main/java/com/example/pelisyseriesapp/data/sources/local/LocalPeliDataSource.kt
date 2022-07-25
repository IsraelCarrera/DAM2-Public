package com.example.pelisyseriesapp.data.sources.local


import com.example.pelisyseriesapp.data.*
import com.example.pelisyseriesapp.data.utils.ResultadoLlamada
import com.example.pelisyseriesapp.domain.GenericoSeriesPelis
import com.example.pelisyseriesapp.domain.Pelicula
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalPeliDataSource @Inject constructor(private val dao: PeliculaLocalDao) {

    suspend fun updatePelicula(pelicula: Pelicula) =
        dao.updatePelicula(pelicula.toPeliculaEntidad())

    suspend fun addPelicula(pelicula: Pelicula) =
        dao.addPelicula(pelicula.toPeliculaRoom())


    suspend fun deletePelicula(idPelicula: Int) =
        dao.deletePelicula(idPelicula)

    //Flows
    fun getAllPeliculas() = dao.getAllPeliculas().map { pelis -> pelis.map { it.toFavoritos() } }
    fun getOnePelicula(id: Int) = dao.getOnePelicula(id)
        .map { pelis -> pelis.map { it.toPelicula() } }

    //Caché
    suspend fun getPelisCache(): ResultadoLlamada<List<GenericoSeriesPelis>> =
        dao.getAllPelisCacheadas().let {
            ResultadoLlamada.Success(it.map { pelis -> pelis.toGenerico() })
        }

    suspend fun todoPeliCache(peli: List<GenericoSeriesPelis>) =
        dao.todoPeliCache(peli.map { it.toPeliculaCache() })
}