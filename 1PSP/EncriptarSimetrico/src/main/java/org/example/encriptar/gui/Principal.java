package org.example.encriptar.gui;


import io.vavr.control.Either;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.example.encriptar.modelo.Secretos;
import org.example.encriptar.servicios.SecretosServices;
import org.example.encriptar.utils.Constantes;

import javax.inject.Inject;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Principal implements Initializable {
    private final SecretosServices secretosServices;
    @FXML
    private ListView<String> lvSecretosDescrifados;
    @FXML
    private TextArea tfSecretoDescifrar;
    @FXML
    private TextField tfNombreUsuario;
    @FXML
    private TextField tfPassUsuario;

    @Inject
    public Principal(SecretosServices ss) {
        this.secretosServices = ss;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void ConsultarSecreto() {
        Either<String, List<String>> secretos = secretosServices.getSecretos(tfNombreUsuario.getText(), tfPassUsuario.getText());
        if (secretos.isRight()) {
            lvSecretosDescrifados.getItems().clear();
            lvSecretosDescrifados.getItems().addAll(secretos.get());
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(secretos.getLeft());
            alert.showAndWait();
        }
    }

    @FXML
    private void AgregarSecreto() {
        Either<String, Boolean> isAdd = secretosServices.addSecreto(recolectarDatosParaGuardar());
        if (isAdd.isRight()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(Constantes.SECRETO_ANADIDO);
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(isAdd.getLeft());
            alert.showAndWait();
        }
    }

    private Secretos recolectarDatosParaGuardar() {
        return Secretos.builder()
                .nombreUser(tfNombreUsuario.getText())
                .passUser(tfPassUsuario.getText())
                .Secreto(tfSecretoDescifrar.getText())
                .build();
    }

}
