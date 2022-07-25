package org.example.clientsecretos.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import lombok.extern.log4j.Log4j2;
import org.example.clientsecretos.dao.utils.UserCacheado;
import org.example.clientsecretos.gui.utils.Constantes;
import org.example.common.modelo.User;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

@Log4j2
public class Principal implements Initializable {
    //Loaders
    private final FXMLLoader fxmlLoaderLoggin;
    private final FXMLLoader fxmlLoaderSecretos;
    private final UserCacheado userCacheado;
    @FXML
    private BorderPane principal;
    @FXML
    private MenuBar idMenuBar;
    private AnchorPane paneLoggin;
    private AnchorPane paneSecretos;
    private Loggin loggin;
    private SecretosPantalla secretosPantalla;
    private User user;


    @Inject
    public Principal(FXMLLoader fxmlLoaderLoggin, FXMLLoader fxmlLoaderSecretos, UserCacheado userCacheado) {
        this.fxmlLoaderLoggin = fxmlLoaderLoggin;
        this.fxmlLoaderSecretos = fxmlLoaderSecretos;
        this.userCacheado = userCacheado;
    }

    public BorderPane getPrincipal() {
        return principal;
    }

    public void setUserActual(User user) {
        this.user = user;
    }


    //carga de pantallas
    private void cargarLoggin() {
        try {
            if (paneLoggin == null) {
                paneLoggin = fxmlLoaderLoggin.load(getClass().getResourceAsStream(Constantes.FXML_LOGGIN_FXML));
                loggin = fxmlLoaderLoggin.getController();
                loggin.setPrincipal(this);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void cargarSecretos() {
        try {
            if (paneSecretos == null) {
                paneSecretos = fxmlLoaderSecretos.load(getClass().getResourceAsStream(Constantes.FXML_SECRETOS_FXML));
                secretosPantalla = fxmlLoaderSecretos.getController();
                secretosPantalla.setPrincipal(this);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    //Los pases

    private void pasarLoggin() {
        if (paneLoggin != null) {
            principal.setCenter(paneLoggin);
        } else {
            mandarAlert(Alert.AlertType.ERROR, Constantes.LA_PANTALLA_NO_SE_HA_PASADO_CORRECTAMENTE);
        }
    }

    private void pasarSecretos() {
        if (paneSecretos != null) {
            principal.setCenter(paneSecretos);
        } else {
            mandarAlert(Alert.AlertType.ERROR, Constantes.LA_PANTALLA_NO_SE_HA_PASADO_CORRECTAMENTE);
        }
    }

    //Los clicks
    @FXML
    private void irLoggin() {
        user = null;
        userCacheado.setNombre(null);
        userCacheado.setPass(null);
        userCacheado.setToken(null);
        userCacheado.setCertificadoBase64(null);
        pasarLoggin();
        noVisible();
    }

    @FXML
    private void irASecretos() {
        secretosPantalla.cargarUser();
        pasarSecretos();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cargarLoggin();
        cargarSecretos();
        pasarLoggin();
        noVisible();
        loggin.cosasNecesarias(paneSecretos, secretosPantalla);
    }

    //Para mandar alerts sin botón de confirmación.
    public void mandarAlert(Alert.AlertType tipoAlerta, String aviso) {
        Alert alerta = new Alert(tipoAlerta);
        alerta.setContentText(aviso);
        alerta.showAndWait();
    }

    private void noVisible() {
        idMenuBar.setVisible(false);
    }

    //Es pública porque se accede desde logging
    public void todoVisible() {
        idMenuBar.setVisible(true);
    }
}
