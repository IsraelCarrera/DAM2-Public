package com.example.appcompose.data.sources.remote

import com.example.appcompose.data.modelo.GetFotoGatos
import com.example.appcompose.data.modelo.LlamadaAPI
import com.example.appcompose.data.modelo.ResultadoLlamada
import javax.inject.Inject

class RemoteGatosDataSource @Inject constructor(
    private val gatosAPI: GatosAPI,
) : LlamadaAPI() {


    suspend fun getFotoPaGato(): ResultadoLlamada<GetFotoGatos> {
        return safeApiCall(
            apiCall =
            { gatosAPI.buscarGato() }
        )
    }
}