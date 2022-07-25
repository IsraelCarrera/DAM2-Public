package com.example.appcompose.data.sources.local.di

import android.content.Context
import androidx.room.Room
import com.example.appcompose.data.sources.local.DatabaseRoom
import com.example.appcompose.data.utils.Constantes
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
    @Named(Constantes.ASSETDB)
    fun getAssetDB() = Constantes.UBI_BBDD

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        @Named(Constantes.ASSETDB) ruta: String,
    ) = Room.databaseBuilder(context, DatabaseRoom::class.java, Constantes.NAME_BBDD)
        .fallbackToDestructiveMigrationFrom(1)
        .createFromAsset(ruta)
        .build()

    @Provides
    fun providesGatitos(articlesDatabase: DatabaseRoom) =
        articlesDatabase.GatitosLocalDao()
}