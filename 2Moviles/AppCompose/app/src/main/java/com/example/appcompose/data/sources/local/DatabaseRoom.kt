package com.example.appcompose.data.sources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.appcompose.data.modelo.GatitosEntidad

@Database(
    entities = [GatitosEntidad::class],
    version = 1,
    exportSchema = true
)

abstract class DatabaseRoom : RoomDatabase() {
    abstract fun GatitosLocalDao(): GatitosLocalDao
}