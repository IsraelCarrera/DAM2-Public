package org.example.clientbasket.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.example.clientbasket.gui.utils.Constantes;
import org.example.common.modelo.Usuario;

import java.net.URL;
import java.util.ResourceBundle;

public class Inicio implements Initializable {

    @FXML
    private Label lbInicio;
    private Usuario usuarioActual;

    public void setUsuarioActual(Usuario usuario) {
        this.usuarioActual = usuario;
    }

    public void ponerFraseInicio() {
        lbInicio.setText(Constantes.BIENVENID_A_LA_APPLICACION_DE_BASKET_QUEVEDO + usuarioActual.getNombreUser());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
