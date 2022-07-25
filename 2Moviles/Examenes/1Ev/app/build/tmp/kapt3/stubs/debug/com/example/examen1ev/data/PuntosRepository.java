package com.example.examen1ev.data;

import com.example.examen1ev.data.modelo.MensajesEntidad;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0019\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\tJ\u0019\u0010\n\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\tJ\u001f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\b0\f2\u0006\u0010\r\u001a\u00020\u000eH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000fJ\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\fH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0012J\u0019\u0010\u0013\u001a\u00020\u00112\u0006\u0010\r\u001a\u00020\u000eH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0014"}, d2 = {"Lcom/example/examen1ev/data/PuntosRepository;", "", "dao", "Lcom/example/examen1ev/data/PuntosDao;", "(Lcom/example/examen1ev/data/PuntosDao;)V", "addMensaje", "", "mensajesEntidad", "Lcom/example/examen1ev/data/modelo/MensajesEntidad;", "(Lcom/example/examen1ev/data/modelo/MensajesEntidad;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteMensaje", "getAllMensajeByIdPunto", "", "id", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllPuntos", "Lcom/example/examen1ev/data/modelo/PuntosConMensajes;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getOnePunto", "app_debug"})
public final class PuntosRepository {
    private final com.example.examen1ev.data.PuntosDao dao = null;
    
    @javax.inject.Inject()
    public PuntosRepository(@org.jetbrains.annotations.NotNull()
    com.example.examen1ev.data.PuntosDao dao) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getAllPuntos(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.examen1ev.data.modelo.PuntosConMensajes>> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object addMensaje(@org.jetbrains.annotations.NotNull()
    com.example.examen1ev.data.modelo.MensajesEntidad mensajesEntidad, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getOnePunto(int id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.examen1ev.data.modelo.PuntosConMensajes> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getAllMensajeByIdPunto(int id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.examen1ev.data.modelo.MensajesEntidad>> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteMensaje(@org.jetbrains.annotations.NotNull()
    com.example.examen1ev.data.modelo.MensajesEntidad mensajesEntidad, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
}