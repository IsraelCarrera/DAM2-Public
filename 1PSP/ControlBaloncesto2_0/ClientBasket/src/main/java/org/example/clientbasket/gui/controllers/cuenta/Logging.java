package org.example.clientbasket.gui.controllers.cuenta;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.example.clientbasket.dao.utils.UserCacheado;
import org.example.clientbasket.gui.controllers.Principal;
import org.example.clientbasket.gui.utils.Constantes;
import org.example.clientbasket.servicios.ServiciosUsuario;
import org.example.common.modelo.Usuario;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class Logging implements Initializable {
    private final ServiciosUsuario su;
    @FXML
    private TextField tfNombreUsuario;
    @FXML
    private TextField tfPassUsuario;
    //Pantalla principal
    private Principal principal;
    private Inicio inicio;
    private AnchorPane paneRegistro;
    private AnchorPane paneInicio;
    private final UserCacheado userCacheado;

    @Inject
    public Logging(ServiciosUsuario su, UserCacheado userCacheado) {
        this.su = su;
        this.userCacheado = userCacheado;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }

    public void panesNecesariosLogging(AnchorPane paneRegistro, AnchorPane paneInicio, Inicio inicio) {
        this.paneRegistro = paneRegistro;
        this.paneInicio = paneInicio;
        this.inicio = inicio;
    }

    @FXML
    private void entrarEnApp() {
        String nombreUser = tfNombreUsuario.getText().toLowerCase();
        String pass = tfPassUsuario.getText();
        if (!nombreUser.isBlank() && !pass.isBlank()) {
            userCacheado.setUsuario(nombreUser);
            userCacheado.setPass(pass);
            Single<Either<String, Usuario>> nombre = su.hacerLogging(nombreUser, pass)
                    .observeOn(JavaFxScheduler.platform())
                    .doFinally(() -> principal.getPrincipal().setCursor(Cursor.DEFAULT));

            nombre.subscribe(either -> either
                            .peek(user -> {
                                principal.setUserActual(user);
                                if (user.getIdTipoUsuario() == 1) {
                                    principal.eresAdmin();
                                } else {
                                    principal.eresNormal();
                                }
                                inicio.setUsuarioActual(user);
                                inicio.ponerFraseInicio();
                                principal.getPrincipal().setCenter(paneInicio);
                            })
                            .peekLeft(fail -> principal.mandarAlert(Alert.AlertType.ERROR, fail)),
                    throwable -> principal.mandarAlert(Alert.AlertType.WARNING, throwable.getMessage()));
            principal.getPrincipal().setCursor(Cursor.WAIT);
            //Para finalizar, mandamos el usuario a la pantalla principal y ponemos la pantalla de inicio
        } else {
            principal.mandarAlert(Alert.AlertType.WARNING, Constantes.NO_HAS_PUESTO_USUARIO_Y_O_PASS);
        }
        //Si es correcto.
    }

    @FXML
    private void irRegistro() {
        if (paneRegistro != null) {
            principal.getPrincipal().setCenter(paneRegistro);
        } else {
            principal.mandarAlert(Alert.AlertType.ERROR, Constantes.LA_PANTALLA_NO_SE_HA_PASADO_CORRECTAMENTE);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
