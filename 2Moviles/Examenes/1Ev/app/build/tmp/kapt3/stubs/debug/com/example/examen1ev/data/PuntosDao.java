package com.example.examen1ev.data;

import androidx.room.*;
import com.example.examen1ev.data.modelo.MensajesEntidad;
import com.example.examen1ev.data.modelo.PuntosConMensajes;

@androidx.room.Dao()
@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u0019\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u001f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\n2\u0006\u0010\u000b\u001a\u00020\fH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\rJ\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\nH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0010J\u0019\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u000b\u001a\u00020\fH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\r\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0012"}, d2 = {"Lcom/example/examen1ev/data/PuntosDao;", "", "addMensaje", "", "mensaje", "Lcom/example/examen1ev/data/modelo/MensajesEntidad;", "(Lcom/example/examen1ev/data/modelo/MensajesEntidad;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteMensaje", "mens", "getAllMensajesByIdPunto", "", "id", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllPuntos", "Lcom/example/examen1ev/data/modelo/PuntosConMensajes;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getOnePunto", "app_debug"})
public abstract interface PuntosDao {
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.Query(value = "Select * from punto")
    public abstract java.lang.Object getAllPuntos(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.examen1ev.data.modelo.PuntosConMensajes>> continuation);
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.Query(value = "select * from punto where id= :id")
    public abstract java.lang.Object getOnePunto(int id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.examen1ev.data.modelo.PuntosConMensajes> continuation);
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.Query(value = "select * from mensaje where idPunto=:id")
    public abstract java.lang.Object getAllMensajesByIdPunto(int id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.examen1ev.data.modelo.MensajesEntidad>> continuation);
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.Insert()
    public abstract java.lang.Object addMensaje(@org.jetbrains.annotations.NotNull()
    com.example.examen1ev.data.modelo.MensajesEntidad mensaje, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.Delete()
    public abstract java.lang.Object deleteMensaje(@org.jetbrains.annotations.NotNull()
    com.example.examen1ev.data.modelo.MensajesEntidad mens, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
}