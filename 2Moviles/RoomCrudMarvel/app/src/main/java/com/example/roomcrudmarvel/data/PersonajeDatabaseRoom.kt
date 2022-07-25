package com.example.roomcrudmarvel.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
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

    companion object {
        @Volatile
        private var INSTANCE: PersonajeDatabaseRoom? = null
        fun getDatabase(context: Context): PersonajeDatabaseRoom {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, PersonajeDatabaseRoom::class.java,
                    "item_database"
                ).createFromAsset("database/heroes.db")
                    .fallbackToDestructiveMigrationFrom(7)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}