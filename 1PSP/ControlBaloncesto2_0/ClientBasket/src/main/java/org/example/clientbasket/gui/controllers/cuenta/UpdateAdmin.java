package org.example.clientbasket.gui.controllers.cuenta;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import org.example.clientbasket.gui.controllers.Principal;
import org.example.clientbasket.gui.utils.Constantes;
import org.example.clientbasket.servicios.ServiciosUsuario;
import org.example.common.modelo.Avisos;
import org.example.common.modelo.Usuario;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;

import javax.inject.Inject;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UpdateAdmin implements Initializable {
    private final ServiciosUsuario su;
    @FXML
    private ListView<Usuario> lvUsuarios;
    //Pantalla principal
    private Principal principal;
    private Usuario usuarioActual;

    @Inject
    public UpdateAdmin(ServiciosUsuario su) {
        this.su = su;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }

    public void setUsuario(Usuario usuario) {
        this.usuarioActual = usuario;
    }

    @FXML
    private void hacerAdmin() {
        if (lvUsuarios.getSelectionModel().getSelectedItem() != null) {
            if (lvUsuarios.getSelectionModel().getSelectedItem().isEstaActivo()) {
                if (lvUsuarios.getSelectionModel().getSelectedItem().getIdTipoUsuario() != 1) {
                    Single<Either<String, Avisos>> singlePartido = su.hacerAdmin(lvUsuarios.getSelectionModel().getSelectedItem().getIdUser(),
                                    usuarioActual.getCorreoElectronico())
                            .observeOn(JavaFxScheduler.platform())
                            .doFinally(() -> principal.getPrincipal().setCursor(Cursor.DEFAULT));
                    singlePartido.subscribe(either -> either
                                    .peek(aviso -> principal.mandarAlert(Alert.AlertType.INFORMATION, aviso.getAviso()))
                                    .peekLeft(fail -> principal.mandarAlert(Alert.AlertType.ERROR, fail)),
                            throwable -> principal.mandarAlert(Alert.AlertType.WARNING, throwable.getMessage()));
                    principal.getPrincipal().setCursor(Cursor.WAIT);
                } else {
                    principal.mandarAlert(Alert.AlertType.WARNING, Constantes.ESE_USUARIO_YA_ES_ADMIN);
                }
            } else {
                principal.mandarAlert(Alert.AlertType.WARNING, Constantes.EL_USUARIO_AUN_NO_ESTA_ACTIVO_HASTA_QUE_NO_ESTE_ACTIVO_NO_PODRAS_HACERLE_ADMIN);
            }
        } else {
            principal.mandarAlert(Alert.AlertType.WARNING, Constantes.NO_HAS_SELECCIONADO_A_NADIE);
        }
    }

    @FXML
    private void eliminarUsuario() {
        if (lvUsuarios.getSelectionModel().getSelectedItem() != null) {
            //Uno mismo NO se puede borrar desde aquí, que lo haga como un usuario normal.
            if (!lvUsuarios.getSelectionModel().getSelectedItem().getNombreUser().equals(usuarioActual.getNombreUser())) {
                Single<Either<String, Integer>> singlePartido = su.deleteUser(lvUsuarios.getSelectionModel().getSelectedItem().getIdUser())
                        .observeOn(JavaFxScheduler.platform())
                        .doFinally(() -> principal.getPrincipal().setCursor(Cursor.DEFAULT));
                singlePartido.subscribe(either -> either
                                .peek(i -> {
                                    principal.mandarAlert(Alert.AlertType.INFORMATION, Constantes.EL_USUARIO_HA_SIDO_BORRADO_CORRECTAMENTE);
                                    lvUsuarios.getItems().remove(lvUsuarios.getSelectionModel().getSelectedItem());
                                })
                                .peekLeft(fail -> principal.mandarAlert(Alert.AlertType.ERROR, fail)),
                        throwable -> principal.mandarAlert(Alert.AlertType.WARNING, throwable.getMessage()));
                principal.getPrincipal().setCursor(Cursor.WAIT);
            } else {
                principal.mandarAlert(Alert.AlertType.WARNING, Constantes.UNO_MISMO_NO_PUEDE_BORRARSE_DESDE_AQUI_SI_DESEA_ELIMINAR_SU_CUENTA_ACCEDA_DESDE_MODIFICAR_CUENTA);
            }
        } else {
            principal.mandarAlert(Alert.AlertType.WARNING, Constantes.NO_HAS_SELECCIONADO_A_NADIE);
        }
    }

    public void cargarUpdateAdmin() {
        Single<Either<String, List<Usuario>>> singlePartido = su.getAllUser()
                .observeOn(JavaFxScheduler.platform())
                .doFinally(() -> principal.getPrincipal().setCursor(Cursor.DEFAULT));

        singlePartido.subscribe(either -> either
                        .peek(usuarios -> {
                            lvUsuarios.getItems().clear();
                            lvUsuarios.getItems().addAll(usuarios);
                        })
                        .peekLeft(fail -> principal.mandarAlert(Alert.AlertType.ERROR, fail)),
                throwable -> principal.mandarAlert(Alert.AlertType.WARNING, throwable.getMessage()));
        principal.getPrincipal().setCursor(Cursor.WAIT);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
