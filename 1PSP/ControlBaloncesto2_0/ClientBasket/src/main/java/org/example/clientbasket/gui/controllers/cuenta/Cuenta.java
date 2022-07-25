package org.example.clientbasket.gui.controllers.cuenta;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import org.example.clientbasket.gui.controllers.Principal;
import org.example.clientbasket.gui.utils.Constantes;
import org.example.clientbasket.servicios.ServiciosUsuario;
import org.example.common.modelo.Avisos;
import org.example.common.modelo.Usuario;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class Cuenta implements Initializable {
    private final ServiciosUsuario su;
    //Pantalla principal
    private Principal principal;
    private AnchorPane paneLogging;
    private Usuario usuarioActual;

    @Inject
    public Cuenta(ServiciosUsuario su) {
        this.su = su;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }

    public void necesarioCuenta(AnchorPane paneLogging) {
        this.paneLogging = paneLogging;
    }

    public void setUsuario(Usuario usuario) {
        this.usuarioActual = usuario;
    }

    //Tengo que pasarle el usuario a este.

    @FXML
    private void eliminarCuenta() {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setContentText(Constantes.ESTAS_SEGURO_DE_QUE_DESEAS_ELIMINAR_LA_CUENTA);
        alerta.showAndWait();
        if (alerta.getResult() == ButtonType.OK) {
            Single<Either<String, Integer>> singlePartido = su.deleteUser(usuarioActual.getIdUser())
                    .observeOn(JavaFxScheduler.platform())
                    .doFinally(() -> principal.getPrincipal().setCursor(Cursor.DEFAULT));

            singlePartido.subscribe(either -> either
                            .peek(aviso -> {
                                principal.mandarAlert(Alert.AlertType.INFORMATION, Constantes.SU_CUENTA_SE_HA_BORRADO_CON_EXITO);
                                principal.setUserActual(null);
                                principal.getPrincipal().setCenter(paneLogging);
                            })
                            .peekLeft(fail -> principal.mandarAlert(Alert.AlertType.ERROR, fail)),
                    throwable -> principal.mandarAlert(Alert.AlertType.WARNING, throwable.getMessage()));
            principal.getPrincipal().setCursor(Cursor.WAIT);
        } else {
            principal.mandarAlert(Alert.AlertType.INFORMATION, Constantes.NO_SE_HA_PROCEDIDO_A_BORRAR_SU_CUENTA);
        }
    }

    @FXML
    private void cambiarPass() {
        Single<Either<String, Avisos>> singlePartido = su.cambiarPass(usuarioActual.getNombreUser())
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> principal.getPrincipal().setCursor(Cursor.DEFAULT));

        singlePartido.subscribe(either -> either
                        .peek(aviso ->
                                principal.mandarAlert(Alert.AlertType.INFORMATION, aviso.getAviso()))
                        .peekLeft(fail -> principal.mandarAlert(Alert.AlertType.ERROR, fail)),
                throwable -> principal.mandarAlert(Alert.AlertType.WARNING, throwable.getMessage()));
        principal.getPrincipal().setCursor(Cursor.WAIT);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
