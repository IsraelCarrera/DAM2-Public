package com.example.pelisyseriesapp.data.sources.local

import androidx.room.*
import com.example.pelisyseriesapp.data.modelo.room.PeliculaRoom
import com.example.pelisyseriesapp.data.modelo.room.entidades.PeliculaCacheEntidad
import com.example.pelisyseriesapp.data.modelo.room.entidades.PeliculaEntidad
import com.example.pelisyseriesapp.data.modelo.room.entidades.PeliculaPersonaParticipanRelacion
import com.example.pelisyseriesapp.data.modelo.room.entidades.PersonaEntidad
import kotlinx.coroutines.flow.Flow

@Dao
interface PeliculaLocalDao {
    //Las selected las dejo porque así se ve mejor el código.

    //El insert de película con su cast.
    //Insertar Pelicula
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(pelicula: PeliculaEntidad)

    //Insertar cast
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCast(cast: List<PersonaEntidad>)

    //Insertar Vinculo
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRelation(rel: List<PeliculaPersonaParticipanRelacion>)

    //Hacemos too.
    @Transaction
    suspend fun addPelicula(pelicula: PeliculaRoom) {
        //Insertamos primero la peli
        insert(pelicula.pelicula)
        //Posteriormente, too el cast
        pelicula.casting?.apply {
            insertCast(this)
        }
        //Por último, la relación
        pelicula.casting?.apply {
            val relacion = mutableListOf<PeliculaPersonaParticipanRelacion>()
            forEach {
                relacion.add(
                    PeliculaPersonaParticipanRelacion(
                        pelicula.pelicula.idPelicula,
                        it.idPersona
                    )
                )
            }
            insertRelation(relacion)
        }
    }
    //Fin del insert pelicula con cast.

    //GetAll
    @Transaction
    @Query("select * from PELICULA")
    fun getAllPeliculas(): Flow<List<PeliculaRoom>>

    //GetOne
    @Transaction
    @Query("select * from PELICULA where idPelicula= :idPelicula")
    fun getOnePelicula(idPelicula: Int): Flow<List<PeliculaRoom>>

    //Update. Para poner si ha visto o no.
    @Update
    suspend fun updatePelicula(pelicula: PeliculaEntidad)

    //Delete.
    @Query("DELETE FROM PELICULA WHERE idPelicula = :idPelicula")
    suspend fun deletePelicula(idPelicula: Int)

    //Cacheos.

    //Se que no es un flow, porque no va a tener cambios, me da igual, solo quiero emitirlos una vez.
    @Query("select * from PELICULACACHE")
    suspend fun getAllPelisCacheadas(): List<PeliculaCacheEntidad>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPeliCacheada(peliculas: List<PeliculaCacheEntidad>)

    @Query("DELETE FROM PELICULACACHE")
    suspend fun deletePelisCacheadas()

    @Transaction
    suspend fun todoPeliCache(peliculas: List<PeliculaCacheEntidad>) {
        deletePelisCacheadas()
        insertPeliCacheada(peliculas)
    }
}