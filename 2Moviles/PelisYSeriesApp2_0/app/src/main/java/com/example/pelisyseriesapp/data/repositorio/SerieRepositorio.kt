package com.example.pelisyseriesapp.data.repositorio

import com.example.pelisyseriesapp.data.sources.local.LocalSerieDataSource
import com.example.pelisyseriesapp.data.sources.remote.RemoteSerieDataSource
import com.example.pelisyseriesapp.data.utils.Constantes
import com.example.pelisyseriesapp.data.utils.ResultadoLlamada
import com.example.pelisyseriesapp.domain.*
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject


@ActivityRetainedScoped
class SerieRepositorio @Inject constructor(
    private val remoteSerieDataSource: RemoteSerieDataSource,
    private val localSerieDataSource: LocalSerieDataSource,
) {


    fun buscarSeries(nombreSerie: String): Flow<ResultadoLlamada<List<GenericoSeriesPelis>>> {
        return flow {
            emit(ResultadoLlamada.Loading())
            //Cacheo
            emit(localSerieDataSource.getSeriesCache())
            //IDem que en pelis, si es "vacio" pues, no se hace
            if (nombreSerie != Constantes.VACIO) {
                val seriesBuscadas = remoteSerieDataSource.buscarSeries(nombreSerie)
                if (seriesBuscadas is ResultadoLlamada.Success) {
                    seriesBuscadas.data?.let {
                        localSerieDataSource.todoSerieCache(it)
                    }
                }
                emit(seriesBuscadas)
            }
        }.flowOn(Dispatchers.IO)
    }


    fun getSerieId(id: Int): Flow<ResultadoLlamada<Serie>> {
        return flow {
            emit(ResultadoLlamada.Loading())
            emit(remoteSerieDataSource.getSerieId(id)
            )
        }.flowOn(Dispatchers.IO)
    }


    fun getCastSerie(id: Int): Flow<ResultadoLlamada<List<ActoresActuan>>> {
        return flow {
            emit(ResultadoLlamada.Loading())
            emit(remoteSerieDataSource.getCreditosSerie(id))
        }.flowOn(Dispatchers.IO)
    }


    fun getEpisodiosTemporada(id: Int, numSeason: Int): Flow<ResultadoLlamada<List<Episodio>>> {
        return flow {
            emit(ResultadoLlamada.Loading())
            emit(remoteSerieDataSource.getEpisodios(id, numSeason))
        }.flowOn(Dispatchers.IO)
    }

    //ROOM
    suspend fun addSerie(serie: Serie) = withContext(Dispatchers.IO) {
        localSerieDataSource.addSerie(serie)
    }

    fun getAllSeriesRoom(): Flow<List<Favoritos>> {
        return localSerieDataSource.getAllSeries().flowOn(Dispatchers.IO)
    }

    fun getOneSerieRoom(id: Int): Flow<List<Serie>> {
        return localSerieDataSource.getOneSerie(id).flowOn(Dispatchers.IO)
    }

    fun getCapitulosTemporadaRoom(idSerie: Int, numSeason: Int): Flow<List<Episodio>> {
        return localSerieDataSource.getCapitulosTemporada(idSerie, numSeason).flowOn(Dispatchers.IO)
    }

    suspend fun updateSerie(serie: Serie) = withContext(Dispatchers.IO) {
        localSerieDataSource.updateSerie(serie)
    }

    suspend fun deleteSerie(idSerie: Int) = withContext(Dispatchers.IO) {
        localSerieDataSource.deleteSerie(idSerie)
    }


    suspend fun updateTemporada(temporada: Temporada) = withContext(Dispatchers.IO) {
        localSerieDataSource.updateTemporada(temporada)
    }

    suspend fun updateEpisodio(episodio: Episodio) = withContext(Dispatchers.IO) {
        localSerieDataSource.updateEpisodio(episodio)
    }
}