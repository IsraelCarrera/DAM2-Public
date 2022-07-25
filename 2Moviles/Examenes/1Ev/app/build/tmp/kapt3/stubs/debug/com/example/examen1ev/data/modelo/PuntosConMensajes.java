package com.example.examen1ev.data.modelo;

import androidx.room.Embedded;
import androidx.room.Relation;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u000e\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0007J\t\u0010\f\u001a\u00020\u0003H\u00c6\u0003J\u0011\u0010\r\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005H\u00c6\u0003J%\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u0010\b\u0002\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005H\u00c6\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0012\u001a\u00020\u0013H\u00d6\u0001J\t\u0010\u0014\u001a\u00020\u0015H\u00d6\u0001R\u001e\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0016"}, d2 = {"Lcom/example/examen1ev/data/modelo/PuntosConMensajes;", "", "puntos", "Lcom/example/examen1ev/data/modelo/PuntosEntidad;", "mensajes", "", "Lcom/example/examen1ev/data/modelo/MensajesEntidad;", "(Lcom/example/examen1ev/data/modelo/PuntosEntidad;Ljava/util/List;)V", "getMensajes", "()Ljava/util/List;", "getPuntos", "()Lcom/example/examen1ev/data/modelo/PuntosEntidad;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "", "app_debug"})
public final class PuntosConMensajes {
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Embedded()
    private final com.example.examen1ev.data.modelo.PuntosEntidad puntos = null;
    @org.jetbrains.annotations.Nullable()
    @androidx.room.Relation(parentColumn = "id", entityColumn = "idPunto")
    private final java.util.List<com.example.examen1ev.data.modelo.MensajesEntidad> mensajes = null;
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.examen1ev.data.modelo.PuntosConMensajes copy(@org.jetbrains.annotations.NotNull()
    com.example.examen1ev.data.modelo.PuntosEntidad puntos, @org.jetbrains.annotations.Nullable()
    java.util.List<com.example.examen1ev.data.modelo.MensajesEntidad> mensajes) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    public PuntosConMensajes(@org.jetbrains.annotations.NotNull()
    com.example.examen1ev.data.modelo.PuntosEntidad puntos, @org.jetbrains.annotations.Nullable()
    java.util.List<com.example.examen1ev.data.modelo.MensajesEntidad> mensajes) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.examen1ev.data.modelo.PuntosEntidad component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.examen1ev.data.modelo.PuntosEntidad getPuntos() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<com.example.examen1ev.data.modelo.MensajesEntidad> component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<com.example.examen1ev.data.modelo.MensajesEntidad> getMensajes() {
        return null;
    }
}