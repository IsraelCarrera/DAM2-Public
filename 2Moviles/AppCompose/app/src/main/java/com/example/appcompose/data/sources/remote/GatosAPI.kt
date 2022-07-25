package com.example.appcompose.data.sources.remote

import com.example.appcompose.data.modelo.GetFotoGatos
import com.example.appcompose.data.utils.Constantes
import retrofit2.Response
import retrofit2.http.GET

interface GatosAPI {

    @GET(Constantes.BUSQUEDA_FOTO)
    suspend fun buscarGato(): Response<GetFotoGatos>
}