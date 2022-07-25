package com.example.pelisyseriesapp.data.sources.local

import androidx.room.*
import com.example.pelisyseriesapp.data.modelo.room.PeliculaRoom
import com.example.pelisyseriesapp.data.modelo.room.entidades.PeliculaEntidad
import com.example.pelisyseriesapp.data.modelo.room.entidades.PeliculaPersonaParticipanRelacion
import com.example.pelisyseriesapp.data.modelo.room.entidades.PersonaEntidad

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
    suspend fun getAllPeliculas(): List<PeliculaRoom>

    //GetOne
    @Transaction
    @Query("select * from PELICULA where idPelicula= :idPelicula")
    suspend fun getOnePelicula(idPelicula: Int): List<PeliculaRoom>

    //Update. Para poner si ha visto o no.
    @Update
    suspend fun updatePelicula(pelicula: PeliculaEntidad)

    //Delete.
    @Delete
    suspend fun deletePelicula(pelicula: PeliculaEntidad)
}