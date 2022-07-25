package org.example.clientsecretos.gui.controllers;

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
import org.example.clientsecretos.gui.utils.Constantes;
import org.example.common.modelo.User;
import org.example.clientsecretos.servicios.UserServicios;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class Loggin implements Initializable {
    private final UserServicios us;
    @FXML
    private TextField tfNombreLog;
    @FXML
    private PasswordField tfPassLog;
    @FXML
    private TextField tfPassCrear;
    @FXML
    private TextField tfNombreCrear;
    private Principal principal;
    private AnchorPane paneSecretos;
    private SecretosPantalla secretosPantalla;

    @Inject
    public Loggin(UserServicios us) {
        this.us = us;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }

    public void cosasNecesarias(AnchorPane paneSecretos, SecretosPantalla secretosPantalla) {
        this.paneSecretos = paneSecretos;
        this.secretosPantalla = secretosPantalla;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void irEntrar() {
        String nombre = tfNombreLog.getText();
        String pass = tfPassLog.getText();
        if (!nombre.isBlank() && !pass.isBlank()) {
            Single<Either<String, User>> singleRegistro = Single.fromCallable(() -> us.hacerLoggin(User.builder().nombre(nombre).pass(pass).build()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(JavaFxScheduler.platform())
                    .doFinally(() -> principal.getPrincipal().setCursor(Cursor.DEFAULT));
            singleRegistro.subscribe(either -> either
                            .peek(e -> {
                                principal.setUserActual(e);
                                principal.todoVisible();
                                secretosPantalla.cargarUser();
                                principal.getPrincipal().setCenter(paneSecretos);
                            })
                            .peekLeft(fail -> principal.mandarAlert(Alert.AlertType.ERROR, fail)),
                    throwable -> principal.mandarAlert(Alert.AlertType.WARNING, throwable.getMessage()));

            principal.getPrincipal().setCursor(Cursor.WAIT);
        }
    }

    @FXML
    private void crearCuenta() {
        String nombre = tfNombreCrear.getText();
        String pass = tfPassCrear.getText();
        if (!nombre.isBlank() && !pass.isBlank()) {
            Single<Either<String, Boolean>> singleCrearCuenta = Single.fromCallable(() -> us.crearUser(User.builder().nombre(nombre).pass(pass).build()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(JavaFxScheduler.platform())
                    .doFinally(() -> principal.getPrincipal().setCursor(Cursor.DEFAULT));
            singleCrearCuenta.subscribe(either -> either
                            .peek(e -> {
                                principal.mandarAlert(Alert.AlertType.INFORMATION, Constantes.SE_HA_CREADO_USUARIO_CORRECTAMENTE);
                            })
                            .peekLeft(fail -> principal.mandarAlert(Alert.AlertType.ERROR, fail)),
                    throwable -> principal.mandarAlert(Alert.AlertType.WARNING, throwable.getMessage()));
            principal.getPrincipal().setCursor(Cursor.WAIT);
        }
    }
}
