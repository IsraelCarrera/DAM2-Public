// Generated by Dagger (https://dagger.dev).
package com.example.examen1ev.data.di;

import com.example.examen1ev.data.PuntosDao;
import com.example.examen1ev.data.PuntosDatabaseRoom;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class RoomModule_ProvidesPersonajeDaoFactory implements Factory<PuntosDao> {
  private final Provider<PuntosDatabaseRoom> articlesDatabaseProvider;

  public RoomModule_ProvidesPersonajeDaoFactory(
      Provider<PuntosDatabaseRoom> articlesDatabaseProvider) {
    this.articlesDatabaseProvider = articlesDatabaseProvider;
  }

  @Override
  public PuntosDao get() {
    return providesPersonajeDao(articlesDatabaseProvider.get());
  }

  public static RoomModule_ProvidesPersonajeDaoFactory create(
      Provider<PuntosDatabaseRoom> articlesDatabaseProvider) {
    return new RoomModule_ProvidesPersonajeDaoFactory(articlesDatabaseProvider);
  }

  public static PuntosDao providesPersonajeDao(PuntosDatabaseRoom articlesDatabase) {
    return Preconditions.checkNotNullFromProvides(RoomModule.INSTANCE.providesPersonajeDao(articlesDatabase));
  }
}
