package org.example.encriptarasimetrico.gui;


import io.vavr.control.Either;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.encriptarasimetrico.gui.utils.Constantes;
import org.example.encriptarasimetrico.modelo.SecretoConId;
import org.example.encriptarasimetrico.servicios.SecretosServices;

import javax.inject.Inject;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Principal implements Initializable {
    private final SecretosServices secretosServices;
    @FXML
    private TextField tfPass;
    @FXML
    private ComboBox<String> cbUserApp;
    @FXML
    private ListView<SecretoConId> lvSecretosDescrifados;
    @FXML
    private TextArea tfSecretoDescifrar;
    @FXML
    private TextField tfNombreUsuario;

    //Esta variable es para guardar la busqueda de la persona que esta ahciendolo, por si acaso.
    private String userPrincipal;
    private String passUserPrincipal;

    @Inject
    public Principal(SecretosServices ss) {
        this.secretosServices = ss;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void consultarSecreto() {
        Either<String, List<SecretoConId>> secretos = secretosServices.getSecretos(tfNombreUsuario.getText().toLowerCase(), tfPass.getText());
        if (secretos.isRight()) {
            lvSecretosDescrifados.getItems().clear();
            lvSecretosDescrifados.getItems().addAll(secretos.get());
            cbUserApp.getItems().clear();
            Either<String, List<String>> listUser = secretosServices.comprobarUsuarios();
            if (listUser.isRight()) {
                cbUserApp.getItems().addAll(listUser.get());
                userPrincipal = tfNombreUsuario.getText().toLowerCase();
                passUserPrincipal = tfPass.getText();
                cbUserApp.getItems().remove(userPrincipal);
            } else {
                mandarAlert(Alert.AlertType.ERROR, listUser.getLeft());
            }
        } else {
            lvSecretosDescrifados.getItems().clear();
            cbUserApp.getItems().clear();
            mandarAlert(Alert.AlertType.ERROR, secretos.getLeft());
        }
    }

    @FXML
    private void agregarSecreto() {
        Either<String, Boolean> isAdd = secretosServices.addSecreto(tfNombreUsuario.getText().toLowerCase(), tfPass.getText(), tfSecretoDescifrar.getText());
        if (isAdd.isRight()) {
            mandarAlert(Alert.AlertType.INFORMATION, Constantes.SECRETO_ANADIDO);

        } else {
            mandarAlert(Alert.AlertType.ERROR, isAdd.getLeft());
        }
    }

    @FXML
    private void compartirConSelect() {

        if (lvSecretosDescrifados.getSelectionModel().getSelectedItem() != null && cbUserApp.getSelectionModel().getSelectedItem() != null) {
            if (!tfNombreUsuario.getText().toLowerCase().equals(cbUserApp.getSelectionModel().getSelectedItem())) {
                Either<String, Boolean> resultado = secretosServices.compartirSecreto(userPrincipal, passUserPrincipal, cbUserApp.getSelectionModel().getSelectedItem(), lvSecretosDescrifados.getSelectionModel().getSelectedItem());
                if (resultado.isRight()) {
                    mandarAlert(Alert.AlertType.INFORMATION, Constantes.SE_HA_COMPARTIDO_EL_SECRETO_CON_DICHA_PERSONA_CORRECTAMENTE);
                } else {
                    mandarAlert(Alert.AlertType.ERROR, Constantes.NO_HA_SIDO_POSIBLE_COMPARTIR_EL_SECRETO);
                }
            } else {
                mandarAlert(Alert.AlertType.ERROR, Constantes.YA_TIENES_EL_SECRETO_COMPARTIDO_CONTIGO_MISMO);
            }
        } else {
            mandarAlert(Alert.AlertType.ERROR, Constantes.CLICK_UN_SECRETO_Y_A_UNA_PERSONA_PARA_COMPARTIRLO);
        }
    }

    private void mandarAlert(Alert.AlertType alertType, String mensaje) {
        Alert alert = new Alert(alertType);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
