package com.example.pelisyseriesapp.data.repositorio

import com.example.pelisyseriesapp.data.sources.local.LocalSerieDataSource
import com.example.pelisyseriesapp.data.sources.remote.RemoteSerieDataSource
import com.example.pelisyseriesapp.domain.Episodio
import com.example.pelisyseriesapp.domain.Serie
import com.example.pelisyseriesapp.domain.Temporada
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ActivityRetainedScoped
class SerieRepositorio @Inject constructor(
    private val remoteSerieDataSource: RemoteSerieDataSource,
    private val localSerieDataSource: LocalSerieDataSource
) {


    //Retrofit
    suspend fun buscarSeries(nombreSerie: String) = withContext(Dispatchers.IO) {
        remoteSerieDataSource.buscarSeries(nombreSerie)
    }

    suspend fun getSerieId(id: Int) = withContext(Dispatchers.IO) {
        remoteSerieDataSource.getSerieId(id)
    }

    suspend fun getCastSerie(id: Int) = withContext(Dispatchers.IO) {
        remoteSerieDataSource.getCreditosSerie(id)
    }

    suspend fun getEpisodiosTemporada(id: Int, numSeason: Int) = withContext(Dispatchers.IO) {
        remoteSerieDataSource.getEpisodios(id, numSeason)
    }

    //ROOM
    suspend fun addSerie(serie: Serie) = withContext(Dispatchers.IO) {
        localSerieDataSource.addSerie(serie)
    }

    suspend fun getAllSeriesRoom() = withContext(Dispatchers.IO) {
        localSerieDataSource.getAllSeries()
    }

    suspend fun getOneSerieRoom(id: Int) = withContext(Dispatchers.IO) {
        localSerieDataSource.getOneSerie(id)
    }

    suspend fun updateSerie(serie: Serie) = withContext(Dispatchers.IO) {
        localSerieDataSource.updateSerie(serie)
    }

    suspend fun deleteSerie(serie: Serie) = withContext(Dispatchers.IO) {
        localSerieDataSource.deleteSerie(serie)
    }

    suspend fun updateTemporada(temporada: Temporada) = withContext(Dispatchers.IO) {
        localSerieDataSource.updateTemporada(temporada)
    }

    suspend fun updateEpisodio(episodio: Episodio) = withContext(Dispatchers.IO) {
        localSerieDataSource.updateEpisodio(episodio)
    }
}