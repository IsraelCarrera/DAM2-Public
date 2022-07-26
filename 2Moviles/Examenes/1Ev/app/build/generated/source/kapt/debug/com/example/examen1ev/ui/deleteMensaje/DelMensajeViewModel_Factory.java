// Generated by Dagger (https://dagger.dev).
package com.example.examen1ev.ui.deleteMensaje;

import com.example.examen1ev.usescases.mensajes.BorrarMensaje;
import com.example.examen1ev.usescases.mensajes.GetAllMensajesByIdItem;
import com.example.examen1ev.usescases.puntos.GetAllPuntos;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import javax.inject.Provider;

@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class DelMensajeViewModel_Factory implements Factory<DelMensajeViewModel> {
  private final Provider<GetAllPuntos> getAllProvider;

  private final Provider<GetAllMensajesByIdItem> getAllMensajesByIdItemProvider;

  private final Provider<BorrarMensaje> borrarMensajeProvider;

  public DelMensajeViewModel_Factory(Provider<GetAllPuntos> getAllProvider,
      Provider<GetAllMensajesByIdItem> getAllMensajesByIdItemProvider,
      Provider<BorrarMensaje> borrarMensajeProvider) {
    this.getAllProvider = getAllProvider;
    this.getAllMensajesByIdItemProvider = getAllMensajesByIdItemProvider;
    this.borrarMensajeProvider = borrarMensajeProvider;
  }

  @Override
  public DelMensajeViewModel get() {
    return newInstance(getAllProvider.get(), getAllMensajesByIdItemProvider.get(), borrarMensajeProvider.get());
  }

  public static DelMensajeViewModel_Factory create(Provider<GetAllPuntos> getAllProvider,
      Provider<GetAllMensajesByIdItem> getAllMensajesByIdItemProvider,
      Provider<BorrarMensaje> borrarMensajeProvider) {
    return new DelMensajeViewModel_Factory(getAllProvider, getAllMensajesByIdItemProvider, borrarMensajeProvider);
  }

  public static DelMensajeViewModel newInstance(GetAllPuntos getAll,
      GetAllMensajesByIdItem getAllMensajesByIdItem, BorrarMensaje borrarMensaje) {
    return new DelMensajeViewModel(getAll, getAllMensajesByIdItem, borrarMensaje);
  }
}
