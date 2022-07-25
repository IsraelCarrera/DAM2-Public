// Generated by Dagger (https://dagger.dev).
package com.example.examen1ev.usescases.mensajes;

import com.example.examen1ev.data.PuntosRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import javax.inject.Provider;

@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class InsertarMensaje_Factory implements Factory<InsertarMensaje> {
  private final Provider<PuntosRepository> puntosProvider;

  public InsertarMensaje_Factory(Provider<PuntosRepository> puntosProvider) {
    this.puntosProvider = puntosProvider;
  }

  @Override
  public InsertarMensaje get() {
    return newInstance(puntosProvider.get());
  }

  public static InsertarMensaje_Factory create(Provider<PuntosRepository> puntosProvider) {
    return new InsertarMensaje_Factory(puntosProvider);
  }

  public static InsertarMensaje newInstance(PuntosRepository puntos) {
    return new InsertarMensaje(puntos);
  }
}
