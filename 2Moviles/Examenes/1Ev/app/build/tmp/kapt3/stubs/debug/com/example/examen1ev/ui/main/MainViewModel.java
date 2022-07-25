package com.example.examen1ev.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.examen1ev.domain.Mensajes;
import com.example.examen1ev.domain.Puntos;
import com.example.examen1ev.usescases.mensajes.InsertarMensaje;
import com.example.examen1ev.usescases.puntos.GetAllPuntos;
import com.example.examen1ev.usescases.puntos.GetOnePunto;
import dagger.hilt.android.lifecycle.HiltViewModel;
import javax.inject.Inject;

@dagger.hilt.android.lifecycle.HiltViewModel()
@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0006\u0010\u0018\u001a\u00020\u0019J\u0010\u0010\u0006\u001a\u0004\u0018\u00010\r2\u0006\u0010\u001a\u001a\u00020\u001bJ\u000e\u0010\u001c\u001a\u00020\u00192\u0006\u0010\u001d\u001a\u00020\u001eR\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u000f0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00118F\u00a2\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\r0\u00118F\u00a2\u0006\u0006\u001a\u0004\b\u0015\u0010\u0013R\u001d\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u000f0\u00118F\u00a2\u0006\u0006\u001a\u0004\b\u0017\u0010\u0013\u00a8\u0006\u001f"}, d2 = {"Lcom/example/examen1ev/ui/main/MainViewModel;", "Landroidx/lifecycle/ViewModel;", "getAll", "Lcom/example/examen1ev/usescases/puntos/GetAllPuntos;", "insertMensaje", "Lcom/example/examen1ev/usescases/mensajes/InsertarMensaje;", "getOnePunto", "Lcom/example/examen1ev/usescases/puntos/GetOnePunto;", "(Lcom/example/examen1ev/usescases/puntos/GetAllPuntos;Lcom/example/examen1ev/usescases/mensajes/InsertarMensaje;Lcom/example/examen1ev/usescases/puntos/GetOnePunto;)V", "_error", "Landroidx/lifecycle/MutableLiveData;", "", "_puntoUno", "Lcom/example/examen1ev/domain/Puntos;", "_puntos", "", "error", "Landroidx/lifecycle/LiveData;", "getError", "()Landroidx/lifecycle/LiveData;", "puntoUno", "getPuntoUno", "puntos", "getPuntos", "getAllPuntos", "", "id", "", "insertarMensaje", "men", "Lcom/example/examen1ev/domain/Mensajes;", "app_debug"})
public final class MainViewModel extends androidx.lifecycle.ViewModel {
    private final com.example.examen1ev.usescases.puntos.GetAllPuntos getAll = null;
    private final com.example.examen1ev.usescases.mensajes.InsertarMensaje insertMensaje = null;
    private final com.example.examen1ev.usescases.puntos.GetOnePunto getOnePunto = null;
    private final androidx.lifecycle.MutableLiveData<java.util.List<com.example.examen1ev.domain.Puntos>> _puntos = null;
    private final androidx.lifecycle.MutableLiveData<java.lang.String> _error = null;
    private final androidx.lifecycle.MutableLiveData<com.example.examen1ev.domain.Puntos> _puntoUno = null;
    
    @javax.inject.Inject()
    public MainViewModel(@org.jetbrains.annotations.NotNull()
    com.example.examen1ev.usescases.puntos.GetAllPuntos getAll, @org.jetbrains.annotations.NotNull()
    com.example.examen1ev.usescases.mensajes.InsertarMensaje insertMensaje, @org.jetbrains.annotations.NotNull()
    com.example.examen1ev.usescases.puntos.GetOnePunto getOnePunto) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.example.examen1ev.domain.Puntos>> getPuntos() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.String> getError() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.example.examen1ev.domain.Puntos> getPuntoUno() {
        return null;
    }
    
    public final void getAllPuntos() {
    }
    
    public final void insertarMensaje(@org.jetbrains.annotations.NotNull()
    com.example.examen1ev.domain.Mensajes men) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.example.examen1ev.domain.Puntos getOnePunto(int id) {
        return null;
    }
}