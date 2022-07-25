package com.example.examen1ev.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.examen1ev.R;
import com.example.examen1ev.databinding.PuntosItemBinding;
import com.example.examen1ev.domain.Puntos;
import com.example.examen1ev.ui.main.PuntosAdapter.PuntosViewHolder;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0002H\u0016J\u0018\u0010\b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0002H\u0016\u00a8\u0006\t"}, d2 = {"Lcom/example/examen1ev/ui/main/DiffCallback;", "Landroidx/recyclerview/widget/DiffUtil$ItemCallback;", "Lcom/example/examen1ev/domain/Puntos;", "()V", "areContentsTheSame", "", "oldItem", "newItem", "areItemsTheSame", "app_debug"})
public final class DiffCallback extends androidx.recyclerview.widget.DiffUtil.ItemCallback<com.example.examen1ev.domain.Puntos> {
    
    public DiffCallback() {
        super();
    }
    
    @java.lang.Override()
    public boolean areItemsTheSame(@org.jetbrains.annotations.NotNull()
    com.example.examen1ev.domain.Puntos oldItem, @org.jetbrains.annotations.NotNull()
    com.example.examen1ev.domain.Puntos newItem) {
        return false;
    }
    
    @java.lang.Override()
    public boolean areContentsTheSame(@org.jetbrains.annotations.NotNull()
    com.example.examen1ev.domain.Puntos oldItem, @org.jetbrains.annotations.NotNull()
    com.example.examen1ev.domain.Puntos newItem) {
        return false;
    }
}