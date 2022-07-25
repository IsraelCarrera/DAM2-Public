package com.example.appcompose.data.sources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appcompose.data.modelo.GatitosEntidad
import kotlinx.coroutines.flow.Flow

@Dao
interface GatitosLocalDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGatito(gatito: GatitosEntidad)

    @Query("Select * from gatitos")
    fun getAllGatitos(): Flow<List<GatitosEntidad>>

    @Query("select * from gatitos where id= :idGatito")
    fun getOneGatito(idGatito: Int): Flow<List<GatitosEntidad>>

    @Query("DELETE FROM gatitos WHERE id = :idGatito")
    suspend fun deleteGatito(idGatito: Int)
}