package com.example.appcompose.data.repositorio

import com.example.appcompose.data.modelo.ResultadoLlamada
import com.example.appcompose.data.sources.local.LocalGatitosDataSource
import com.example.appcompose.data.sources.remote.RemoteGatosDataSource
import com.example.appcompose.domain.Gatos
import com.example.appcompose.domain.GatosLista
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ActivityRetainedScoped
class GatosRepository @Inject constructor(
    private val localGatitosDataSource: LocalGatitosDataSource,
    private val remoteGatosDataSource: RemoteGatosDataSource,
) {
    suspend fun addGatito(gato: Gatos) = withContext(Dispatchers.IO) {
        val fotico = remoteGatosDataSource.getFotoPaGato()
        if (fotico is ResultadoLlamada.Success) {
            if(fotico.data?.get(0)?.url != null){
            gato.foto = fotico.data[0].url
            }
        }
        localGatitosDataSource.addGato(gatos = gato)
    }

    fun getAllGatos(): Flow<List<GatosLista>> {
        return localGatitosDataSource.getAllGatos()
    }

    fun getOneGatito(id: Int): Flow<List<Gatos>> {
        return localGatitosDataSource.getOneGatito(id)
    }

    suspend fun deleteGato(id: Int) = withContext(Dispatchers.IO) {
        localGatitosDataSource.deleteGato(id)
    }
}