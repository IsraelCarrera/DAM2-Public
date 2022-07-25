package com.example.roomcrudmarvel.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.roomcrudmarvel.data.modelo.ComicEntidad
import com.example.roomcrudmarvel.data.modelo.PersonajeEntidad


@Database(
    entities = [PersonajeEntidad::class, ComicEntidad::class],
    version = 7,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class PersonajeDatabaseRoom : RoomDatabase() {
    abstract fun personajeDao(): PersonajeDao
}