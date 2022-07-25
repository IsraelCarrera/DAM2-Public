package com.example.examen1ev.ui.deleteMensaje;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.examen1ev.databinding.DeleteMensajesBinding;
import com.example.examen1ev.domain.Mensajes;
import com.example.examen1ev.domain.Puntos;
import com.example.examen1ev.ui.main.PuntosAdapter;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import dagger.hilt.android.AndroidEntryPoint;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0012\u0010\u0013\u001a\u00020\u00102\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0014J\u0010\u0010\u0016\u001a\u00020\u00102\u0006\u0010\u0017\u001a\u00020\u0018H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u001b\u0010\t\u001a\u00020\n8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u0019"}, d2 = {"Lcom/example/examen1ev/ui/deleteMensaje/DeleteMensajeActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/example/examen1ev/databinding/DeleteMensajesBinding;", "mensajesAdapter", "Lcom/example/examen1ev/ui/deleteMensaje/MensajesAdapter;", "puntosAdapter", "Lcom/example/examen1ev/ui/main/PuntosAdapter;", "viewModel", "Lcom/example/examen1ev/ui/deleteMensaje/DelMensajeViewModel;", "getViewModel", "()Lcom/example/examen1ev/ui/deleteMensaje/DelMensajeViewModel;", "viewModel$delegate", "Lkotlin/Lazy;", "deleteMensaje", "", "men", "Lcom/example/examen1ev/domain/Mensajes;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "verMensajes", "punt", "Lcom/example/examen1ev/domain/Puntos;", "app_debug"})
@dagger.hilt.android.AndroidEntryPoint()
public final class DeleteMensajeActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.example.examen1ev.databinding.DeleteMensajesBinding binding;
    private com.example.examen1ev.ui.main.PuntosAdapter puntosAdapter;
    private com.example.examen1ev.ui.deleteMensaje.MensajesAdapter mensajesAdapter;
    private final kotlin.Lazy viewModel$delegate = null;
    
    public DeleteMensajeActivity() {
        super();
    }
    
    private final com.example.examen1ev.ui.deleteMensaje.DelMensajeViewModel getViewModel() {
        return null;
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void verMensajes(com.example.examen1ev.domain.Puntos punt) {
    }
    
    private final void deleteMensaje(com.example.examen1ev.domain.Mensajes men) {
    }
}