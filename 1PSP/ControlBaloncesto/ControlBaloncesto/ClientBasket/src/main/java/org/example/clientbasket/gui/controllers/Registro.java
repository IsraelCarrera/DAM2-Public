package org.example.clientbasket.gui.controllers;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.example.clientbasket.gui.utils.Constantes;
import org.example.clientbasket.servicios.ServiciosUsuario;
import org.example.common.modelo.Usuario;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class Registro implements Initializable {
    private final ServiciosUsuario su;
    @FXML
    private TextField tfNombreUsuario;
    @FXML
    private TextField tfCorreo;
    @FXML
    private PasswordField tfPass;
    @FXML
    private PasswordField tfRepitePass;
    //Pantalla principal
    private Principal principal;
    private AnchorPane paneLogging;

    @Inject
    public Registro(ServiciosUsuario su) {
        this.su = su;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }

    public void panesNecesariosRegistro(AnchorPane paneLogging) {
        this.paneLogging = paneLogging;
    }

    @FXML
    private void volverAlLogging() {
        if (paneLogging != null) {
            principal.getPrincipal().setCenter(paneLogging);
        } else {
            principal.mandarAlert(Alert.AlertType.ERROR, Constantes.LA_PANTALLA_NO_SE_HA_PASADO_CORRECTAMENTE);
        }
    }

    @FXML
    private void hacerRegistro() {
        String nombreUsuario = tfNombreUsuario.getText().toLowerCase();
        String pass = tfPass.getText();
        String passComprobar = tfRepitePass.getText();
        String email = tfCorreo.getText().toLowerCase();
        //Comprobamos que TODOS los campos estén correctos. Lo haré en una función aparte.
        if (comprobarCampos(nombreUsuario, pass, passComprobar, email)) {
            //Creamos el usuario.
            Usuario user = Usuario.builder()
                    .nombreUser(nombreUsuario)
                    .pass(pass)
                    .correoElectronico(email)
                    .build();
            //Hacemos la petición
            Single<Either<String, Usuario>> nombre = Single.fromCallable(() -> su.addUser(user))
                    .subscribeOn(Schedulers.io())
                    .observeOn(JavaFxScheduler.platform())
                    .doFinally(() -> principal.getPrincipal().setCursor(Cursor.DEFAULT));

            nombre.subscribe(either -> either
                            .peek(usuario -> {
                                principal.mandarAlert(Alert.AlertType.INFORMATION, Constantes.SE_HA_REGISTRADO_EN_LA_APLICACION_CORRECTAMENTE_COMPRUEBE_SU_CORREO);
                                principal.getPrincipal().setCenter(paneLogging);
                            })
                            .peekLeft(fail -> principal.mandarAlert(Alert.AlertType.ERROR, fail)),
                    throwable -> principal.mandarAlert(Alert.AlertType.WARNING, throwable.getMessage()));
            principal.getPrincipal().setCursor(Cursor.WAIT);
        } else {
            principal.mandarAlert(Alert.AlertType.ERROR, Constantes.RELLENE_TODOS_LOS_CAMPOS_CORRECTAMENTE);
        }
    }

    private boolean comprobarCampos(String nombreUsuario, String pass, String passComprobar, String email) {
        boolean esCorrecto = true;
        if (nombreUsuario.isBlank() && pass.isBlank() && passComprobar.isBlank() && email.isBlank()) {
            //Comprobamos vacios.
            esCorrecto = false;
        } else if (!pass.equals(passComprobar)) {
            //Comprobamos si se repiten las pass.
            esCorrecto = false;
        } else if (!email.matches("^(.+)@(.+)$")) {
            //Comprobamos que es un email.
            esCorrecto = false;
        }
        return esCorrecto;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
