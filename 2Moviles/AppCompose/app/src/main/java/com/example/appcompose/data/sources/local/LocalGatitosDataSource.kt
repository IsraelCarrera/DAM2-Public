package com.example.appcompose.data.sources.local

import com.example.appcompose.data.toGatos
import com.example.appcompose.data.toGatosEntidad
import com.example.appcompose.data.toGatosLista
import com.example.appcompose.domain.Gatos
import com.example.appcompose.domain.GatosLista
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalGatitosDataSource @Inject constructor(private val dao: GatitosLocalDao) {

    suspend fun addGato(gatos: Gatos) = dao.insertGatito(gatos.toGatosEntidad())

    fun getAllGatos(): Flow<List<GatosLista>> =
        dao.getAllGatitos().map { gatos -> gatos.map { it.toGatosLista() } }

    fun getOneGatito(id: Int) = dao.getOneGatito(id).map { gatito -> gatito.map { it.toGatos() } }

    suspend fun deleteGato(idGato: Int) = dao.deleteGatito(idGato)
}