package com.example.roomcrudmarvel.data

import androidx.room.*
import com.example.roomcrudmarvel.data.modelo.ComicEntidad
import com.example.roomcrudmarvel.data.modelo.PersonajeConComics
import com.example.roomcrudmarvel.data.modelo.PersonajeEntidad

@Dao
interface PersonajeDao {

    @Query("Select * from Heroes")
    suspend fun getAllPersonajes(): List<PersonajeConComics>

    @Update
    suspend fun updatePersonaje(pers: PersonajeEntidad)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: PersonajeEntidad): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: ComicEntidad)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: List<ComicEntidad>)

    @Transaction
    suspend fun addPersonaje(pers: PersonajeConComics) {
        pers.heroe.id = insert(pers.heroe).toInt()
        pers.comics?.apply {
            forEach { it.idPersonaje = pers.heroe.id }
            insert(this)
        }
    }

    @Delete
    suspend fun delete(pers: PersonajeEntidad, comics: List<ComicEntidad>?)
}