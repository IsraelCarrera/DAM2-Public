package com.example.roomcrudmarvel.data.di

import android.content.Context
import androidx.room.Room
import com.example.roomcrudmarvel.data.Constantes
import com.example.roomcrudmarvel.data.PersonajeDatabaseRoom
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
    @Named("assetDB")
    fun getAssetDB() = Constantes.UBI_TABLA

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        @Named("assetDB") ruta: String
    ) = Room.databaseBuilder(context, PersonajeDatabaseRoom::class.java, Constantes.NOMBRE_DB)
        .fallbackToDestructiveMigrationFrom(7)
        .createFromAsset(ruta)
        .build()

    @Provides
    fun providesPersonajeDao(articlesDatabase: PersonajeDatabaseRoom) =
        articlesDatabase.personajeDao()
}