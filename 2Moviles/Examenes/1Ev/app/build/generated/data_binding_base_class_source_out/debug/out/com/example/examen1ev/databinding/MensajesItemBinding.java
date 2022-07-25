// Generated by view binder compiler. Do not edit!
package com.example.examen1ev.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.examen1ev.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class MensajesItemBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button btnDeleteMensaje;

  @NonNull
  public final TextView tvAutor;

  @NonNull
  public final TextView tvIdMensajes;

  @NonNull
  public final TextView tvMensaje;

  private MensajesItemBinding(@NonNull ConstraintLayout rootView, @NonNull Button btnDeleteMensaje,
      @NonNull TextView tvAutor, @NonNull TextView tvIdMensajes, @NonNull TextView tvMensaje) {
    this.rootView = rootView;
    this.btnDeleteMensaje = btnDeleteMensaje;
    this.tvAutor = tvAutor;
    this.tvIdMensajes = tvIdMensajes;
    this.tvMensaje = tvMensaje;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static MensajesItemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static MensajesItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.mensajes_item, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static MensajesItemBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnDeleteMensaje;
      Button btnDeleteMensaje = ViewBindings.findChildViewById(rootView, id);
      if (btnDeleteMensaje == null) {
        break missingId;
      }

      id = R.id.tvAutor;
      TextView tvAutor = ViewBindings.findChildViewById(rootView, id);
      if (tvAutor == null) {
        break missingId;
      }

      id = R.id.tvIdMensajes;
      TextView tvIdMensajes = ViewBindings.findChildViewById(rootView, id);
      if (tvIdMensajes == null) {
        break missingId;
      }

      id = R.id.tvMensaje;
      TextView tvMensaje = ViewBindings.findChildViewById(rootView, id);
      if (tvMensaje == null) {
        break missingId;
      }

      return new MensajesItemBinding((ConstraintLayout) rootView, btnDeleteMensaje, tvAutor,
          tvIdMensajes, tvMensaje);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}