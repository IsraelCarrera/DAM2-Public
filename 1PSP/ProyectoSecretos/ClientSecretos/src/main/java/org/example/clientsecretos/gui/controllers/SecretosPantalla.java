package org.example.clientsecretos.gui.controllers;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.util.StringConverter;
import org.example.clientsecretos.domain.Secretos;
import org.example.clientsecretos.gui.utils.Constantes;
import org.example.clientsecretos.servicios.SecretosServicios;
import org.example.clientsecretos.servicios.UserServicios;
import org.example.common.modelo.Info;
import org.example.common.modelo.User;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;

import javax.inject.Inject;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SecretosPantalla implements Initializable {

    private final SecretosServicios ss;
    private final UserServicios us;
    private Principal principal;
    @FXML
    private TextArea tfSecreto;
    @FXML
    private ComboBox<User> cbUsuarios;
    @FXML
    private ListView<Secretos> lvListaSecretos;

    @Inject
    public SecretosPantalla(SecretosServicios ss, UserServicios us) {
        this.ss = ss;
        this.us = us;
    }


    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void addSecreto() {
        String secreto = tfSecreto.getText();
        if (!secreto.isBlank()) {
            Single<Either<String, Info>> singleAddSecreto = Single.fromCallable(() -> ss.addSecreto(secreto))
                    .subscribeOn(Schedulers.io())
                    .observeOn(JavaFxScheduler.platform())
                    .doFinally(() -> principal.getPrincipal().setCursor(Cursor.DEFAULT));
            singleAddSecreto.subscribe(either -> either
                            .peek(e -> {
                                principal.mandarAlert(Alert.AlertType.INFORMATION, e.getMensaje());
                            })
                            .peekLeft(fail -> principal.mandarAlert(Alert.AlertType.ERROR, fail)),
                    throwable -> principal.mandarAlert(Alert.AlertType.WARNING, throwable.getMessage()));
            principal.getPrincipal().setCursor(Cursor.WAIT);
        }
    }

    public void cargarUser() {
        lvListaSecretos.getItems().clear();
        Single<Either<String, List<User>>> singleAddSecreto = Single.fromCallable(us::getAllUsers)
                .subscribeOn(Schedulers.io())
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> principal.getPrincipal().setCursor(Cursor.DEFAULT));
        singleAddSecreto.subscribe(either -> either
                        .peek(e -> {
                            cbUsuarios.getItems().clear();
                            cbUsuarios.setConverter(new StringConverter<User>() {
                                @Override
                                public String toString(User u) {
                                    return u == null ? null : u.getNombre();
                                }

                                @Override
                                public User fromString(String s) {
                                    return null;
                                }
                            });
                            cbUsuarios.getItems().addAll(e);
                        })
                        .peekLeft(fail -> principal.mandarAlert(Alert.AlertType.ERROR, fail)),
                throwable -> principal.mandarAlert(Alert.AlertType.WARNING, throwable.getMessage()));
        principal.getPrincipal().setCursor(Cursor.WAIT);
    }

    @FXML
    private void buscarSecreto() {
        Single<Either<String, List<Secretos>>> singleAddSecreto = Single.fromCallable(() -> ss.getAllSecretos(cbUsuarios.getItems()))
                .subscribeOn(Schedulers.io())
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> principal.getPrincipal().setCursor(Cursor.DEFAULT));
        singleAddSecreto.subscribe(either -> either
                        .peek(e -> {
                            lvListaSecretos.getItems().clear();
                            lvListaSecretos.getItems().addAll(e);
                        })
                        .peekLeft(fail -> principal.mandarAlert(Alert.AlertType.ERROR, fail)),
                throwable -> principal.mandarAlert(Alert.AlertType.WARNING, throwable.getMessage()));
        principal.getPrincipal().setCursor(Cursor.WAIT);
    }

    @FXML
    private void compartirSecreto() {
        if (lvListaSecretos.getSelectionModel().getSelectedItem() != null && cbUsuarios.getSelectionModel().getSelectedItem() != null) {
            User user = cbUsuarios.getSelectionModel().getSelectedItem();
            if (user != null) {
                Single<Either<String, Info>> singleAddSecreto = Single.fromCallable(() -> ss.compartirSecretos(lvListaSecretos.getSelectionModel().getSelectedItem(), user))
                        .subscribeOn(Schedulers.io())
                        .observeOn(JavaFxScheduler.platform())
                        .doFinally(() -> principal.getPrincipal().setCursor(Cursor.DEFAULT));
                singleAddSecreto.subscribe(either -> either
                                .peek(e -> {
                                    principal.mandarAlert(Alert.AlertType.INFORMATION, e.getMensaje());
                                })
                                .peekLeft(fail -> principal.mandarAlert(Alert.AlertType.ERROR, fail)),
                        throwable -> principal.mandarAlert(Alert.AlertType.WARNING, throwable.getMessage()));
                principal.getPrincipal().setCursor(Cursor.WAIT);
            } else {
                principal.mandarAlert(Alert.AlertType.ERROR, Constantes.ERROR_AL_ELEGIR_EL_USUARIO);
            }
        }
    }
}
