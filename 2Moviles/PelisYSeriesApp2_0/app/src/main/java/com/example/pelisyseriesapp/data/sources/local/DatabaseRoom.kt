package com.example.pelisyseriesapp.data.sources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pelisyseriesapp.data.Converters
import com.example.pelisyseriesapp.data.modelo.room.entidades.*


@Database(
    entities = [PeliculaEntidad::class, PersonaEntidad::class, PeliculaPersonaParticipanRelacion::class,
        SerieEntidad::class, TemporadaEntidad::class, CapituloEntidad::class, SeriePersonaParticipanRelacion::class,
        SerieCacheEntidad::class, PeliculaCacheEntidad::class],
    version = 10,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class DatabaseRoom : RoomDatabase() {
    abstract fun SerieLocalDao(): SerieLocalDao
    abstract fun peliculaLocalDao(): PeliculaLocalDao
}

