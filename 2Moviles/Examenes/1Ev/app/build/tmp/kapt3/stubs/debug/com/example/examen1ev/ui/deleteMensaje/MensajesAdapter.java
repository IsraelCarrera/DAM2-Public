package com.example.examen1ev.ui.deleteMensaje;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.example.examen1ev.R;
import com.example.examen1ev.databinding.MensajesItemBinding;
import com.example.examen1ev.domain.Mensajes;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0001\u0010B\u0019\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\u0002\u0010\u0007J\u0018\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0018\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000bH\u0016R\u001a\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"Lcom/example/examen1ev/ui/deleteMensaje/MensajesAdapter;", "Landroidx/recyclerview/widget/ListAdapter;", "Lcom/example/examen1ev/domain/Mensajes;", "Lcom/example/examen1ev/ui/deleteMensaje/MensajesAdapter$MensajesViewHolder;", "deleteMensaje", "Lkotlin/Function1;", "", "(Lkotlin/jvm/functions/Function1;)V", "onBindViewHolder", "holder", "position", "", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "MensajesViewHolder", "app_debug"})
public final class MensajesAdapter extends androidx.recyclerview.widget.ListAdapter<com.example.examen1ev.domain.Mensajes, com.example.examen1ev.ui.deleteMensaje.MensajesAdapter.MensajesViewHolder> {
    private final kotlin.jvm.functions.Function1<com.example.examen1ev.domain.Mensajes, kotlin.Unit> deleteMensaje = null;
    
    public MensajesAdapter(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.example.examen1ev.domain.Mensajes, kotlin.Unit> deleteMensaje) {
        super(null);
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public com.example.examen1ev.ui.deleteMensaje.MensajesAdapter.MensajesViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.example.examen1ev.ui.deleteMensaje.MensajesAdapter.MensajesViewHolder holder, int position) {
    }
    
    @kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\"\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\b0\fR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lcom/example/examen1ev/ui/deleteMensaje/MensajesAdapter$MensajesViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Landroid/view/View;)V", "binding", "Lcom/example/examen1ev/databinding/MensajesItemBinding;", "bind", "", "men", "Lcom/example/examen1ev/domain/Mensajes;", "deleteMensaje", "Lkotlin/Function1;", "app_debug"})
    public static final class MensajesViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        private final com.example.examen1ev.databinding.MensajesItemBinding binding = null;
        
        public MensajesViewHolder(@org.jetbrains.annotations.NotNull()
        android.view.View itemView) {
            super(null);
        }
        
        public final void bind(@org.jetbrains.annotations.NotNull()
        com.example.examen1ev.domain.Mensajes men, @org.jetbrains.annotations.NotNull()
        kotlin.jvm.functions.Function1<? super com.example.examen1ev.domain.Mensajes, kotlin.Unit> deleteMensaje) {
        }
    }
}