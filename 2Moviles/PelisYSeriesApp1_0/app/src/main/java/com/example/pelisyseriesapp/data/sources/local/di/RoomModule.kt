package com.example.pelisyseriesapp.data.sources.local.di

import android.content.Context
import androidx.room.Room
import com.example.pelisyseriesapp.data.sources.local.DatabaseRoom
import com.example.pelisyseriesapp.data.utils.Constantes
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Provides
    @Named(Constantes.ASSET_DB)
    fun getAssetDB() = Constantes.UBI_TABLA

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        @Named(Constantes.ASSET_DB) ruta: String
    ) = Room.databaseBuilder(context, DatabaseRoom::class.java, Constantes.NOMBRE_DB)
        .fallbackToDestructiveMigrationFrom(9)
        .createFromAsset(ruta)
        .build()

    @Provides
    fun providesPeliculaDao(articlesDatabase: DatabaseRoom) =
        articlesDatabase.peliculaLocalDao()

    @Provides
    fun providesSerieDao(articlesDatabase: DatabaseRoom) =
        articlesDatabase.SerieLocalDao()
}