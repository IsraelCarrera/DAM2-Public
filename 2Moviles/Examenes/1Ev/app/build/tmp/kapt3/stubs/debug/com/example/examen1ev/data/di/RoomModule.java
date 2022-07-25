package com.example.examen1ev.data.di;

import android.content.Context;
import androidx.room.Room;
import com.example.examen1ev.data.Constantes;
import com.example.examen1ev.data.PuntosDatabaseRoom;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Named;
import javax.inject.Singleton;

@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0007J\u001c\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u0007\u001a\u00020\b2\b\b\u0001\u0010\t\u001a\u00020\u0004H\u0007J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0006H\u0007\u00a8\u0006\r"}, d2 = {"Lcom/example/examen1ev/data/di/RoomModule;", "", "()V", "getAssetDB", "", "provideDatabase", "Lcom/example/examen1ev/data/PuntosDatabaseRoom;", "context", "Landroid/content/Context;", "ruta", "providesPersonajeDao", "Lcom/example/examen1ev/data/PuntosDao;", "articlesDatabase", "app_debug"})
@dagger.Module()
public final class RoomModule {
    @org.jetbrains.annotations.NotNull()
    public static final com.example.examen1ev.data.di.RoomModule INSTANCE = null;
    
    private RoomModule() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    @javax.inject.Named(value = "assetDB")
    @dagger.Provides()
    public final java.lang.String getAssetDB() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @javax.inject.Singleton()
    @dagger.Provides()
    public final com.example.examen1ev.data.PuntosDatabaseRoom provideDatabase(@org.jetbrains.annotations.NotNull()
    @dagger.hilt.android.qualifiers.ApplicationContext()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    @javax.inject.Named(value = "assetDB")
    java.lang.String ruta) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Provides()
    public final com.example.examen1ev.data.PuntosDao providesPersonajeDao(@org.jetbrains.annotations.NotNull()
    com.example.examen1ev.data.PuntosDatabaseRoom articlesDatabase) {
        return null;
    }
}