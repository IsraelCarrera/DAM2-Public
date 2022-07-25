package com.example.examen1ev.ui.deleteMensaje;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.examen1ev.domain.Mensajes;
import com.example.examen1ev.domain.Puntos;
import com.example.examen1ev.usescases.mensajes.BorrarMensaje;
import com.example.examen1ev.usescases.mensajes.GetAllMensajesByIdItem;
import com.example.examen1ev.usescases.puntos.GetAllPuntos;
import dagger.hilt.android.lifecycle.HiltViewModel;
import javax.inject.Inject;

@dagger.hilt.android.lifecycle.HiltViewModel()
@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u000e\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u000eJ\u000e\u0010\u0004\u001a\u00020\u001a2\u0006\u0010\u001c\u001a\u00020\u001dJ\u0006\u0010\u001e\u001a\u00020\u001aR\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\r0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00128F\u00a2\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\u00128F\u00a2\u0006\u0006\u001a\u0004\b\u0016\u0010\u0014R\u001d\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\r0\u00128F\u00a2\u0006\u0006\u001a\u0004\b\u0018\u0010\u0014\u00a8\u0006\u001f"}, d2 = {"Lcom/example/examen1ev/ui/deleteMensaje/DelMensajeViewModel;", "Landroidx/lifecycle/ViewModel;", "getAll", "Lcom/example/examen1ev/usescases/puntos/GetAllPuntos;", "getAllMensajesByIdItem", "Lcom/example/examen1ev/usescases/mensajes/GetAllMensajesByIdItem;", "borrarMensaje", "Lcom/example/examen1ev/usescases/mensajes/BorrarMensaje;", "(Lcom/example/examen1ev/usescases/puntos/GetAllPuntos;Lcom/example/examen1ev/usescases/mensajes/GetAllMensajesByIdItem;Lcom/example/examen1ev/usescases/mensajes/BorrarMensaje;)V", "_error", "Landroidx/lifecycle/MutableLiveData;", "", "_mensajes", "", "Lcom/example/examen1ev/domain/Mensajes;", "_puntos", "Lcom/example/examen1ev/domain/Puntos;", "error", "Landroidx/lifecycle/LiveData;", "getError", "()Landroidx/lifecycle/LiveData;", "mensajes", "getMensajes", "puntos", "getPuntos", "deleteMensaje", "", "men", "id", "", "getAllPuntos", "app_debug"})
public final class DelMensajeViewModel extends androidx.lifecycle.ViewModel {
    private final com.example.examen1ev.usescases.puntos.GetAllPuntos getAll = null;
    private final com.example.examen1ev.usescases.mensajes.GetAllMensajesByIdItem getAllMensajesByIdItem = null;
    private final com.example.examen1ev.usescases.mensajes.BorrarMensaje borrarMensaje = null;
    private final androidx.lifecycle.MutableLiveData<java.util.List<com.example.examen1ev.domain.Puntos>> _puntos = null;
    private final androidx.lifecycle.MutableLiveData<java.util.List<com.example.examen1ev.domain.Mensajes>> _mensajes = null;
    private final androidx.lifecycle.MutableLiveData<java.lang.String> _error = null;
    
    @javax.inject.Inject()
    public DelMensajeViewModel(@org.jetbrains.annotations.NotNull()
    com.example.examen1ev.usescases.puntos.GetAllPuntos getAll, @org.jetbrains.annotations.NotNull()
    com.example.examen1ev.usescases.mensajes.GetAllMensajesByIdItem getAllMensajesByIdItem, @org.jetbrains.annotations.NotNull()
    com.example.examen1ev.usescases.mensajes.BorrarMensaje borrarMensaje) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.example.examen1ev.domain.Puntos>> getPuntos() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.example.examen1ev.domain.Mensajes>> getMensajes() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.String> getError() {
        return null;
    }
    
    public final void getAllPuntos() {
    }
    
    public final void getAllMensajesByIdItem(int id) {
    }
    
    public final void deleteMensaje(@org.jetbrains.annotations.NotNull()
    com.example.examen1ev.domain.Mensajes men) {
    }
}