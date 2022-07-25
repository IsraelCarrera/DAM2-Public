package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class PrinciPantalla implements Initializable {
    //Variables del FXML
    @FXML
    private BorderPane principal;

    private final String NO_CARGAR_PANTALLA = "No se ha podido cargar la pantalla";
    private final Alert alerta = new Alert(Alert.AlertType.WARNING);


    private AnchorPane paneAddPantalla;

    private AnchorPane panePantallaPantallaInicio;

    private AnchorPane paneDeletePantalla;
    private DeletePantalla deletePantalla;

    private AnchorPane paneUpdatePantalla;
    private UpdatePantalla updatePantalla;

    private AnchorPane paneViewPantalla;
    private ViewPantalla viewPantalla;

    //Cargar las pantallas.
    private void cargarInicio() {
        FXMLLoader fxmlloaderPantallaInicio = new FXMLLoader();
        try {
            if (panePantallaPantallaInicio == null) {
                panePantallaPantallaInicio = fxmlloaderPantallaInicio.load(getClass().getResourceAsStream("/fxml/inicioPantalla.fxml"));
            }
        } catch (Exception e) {
            System.err.println("No se ha cargado la pantallaPrincipal");
        }
    }

    private void cargarAddPantalla() {
        //FXML y AnchorPanes
        FXMLLoader fxmlloaderAddPantalla = new FXMLLoader();
        try {
            if (paneAddPantalla == null) {
                paneAddPantalla = fxmlloaderAddPantalla.load(getClass().getResourceAsStream("/fxml/addPantalla.fxml"));
            }
        } catch (Exception e) {
            System.err.println("No se ha cargado la pantalla AddPantalla");
        }
    }

    private void cargarDelete() {
        FXMLLoader fxmlloaderDeletePantalla = new FXMLLoader();
        try {
            if (paneDeletePantalla == null) {
                paneDeletePantalla = fxmlloaderDeletePantalla.load(getClass().getResourceAsStream("/fxml/deletePantalla.fxml"));
                deletePantalla = fxmlloaderDeletePantalla.getController();
            }
        } catch (Exception e) {
            System.err.println("NO se ha cargado la pantallaDelete");
        }
    }

    private void cargarUpdate() {
        FXMLLoader fxmlloaderUpdatePantalla = new FXMLLoader();
        try {
            if (paneUpdatePantalla == null) {
                paneUpdatePantalla = fxmlloaderUpdatePantalla.load(getClass().getResourceAsStream("/fxml/updatePantalla.fxml"));
                updatePantalla = fxmlloaderUpdatePantalla.getController();
            }
        } catch (Exception e) {
            System.err.println("No se ha cargado la pantallaUpdate");
        }
    }

    private void cargarView() {
        FXMLLoader fxmlloaderViewPantalla = new FXMLLoader();
        try {
            if (paneViewPantalla == null) {
                paneViewPantalla = fxmlloaderViewPantalla.load(getClass().getResourceAsStream("/fxml/viewPantalla.fxml"));
                viewPantalla = fxmlloaderViewPantalla.getController();
            }
        } catch (Exception e) {
            System.err.println("No se ha cargado la pantallaView");
        }
    }

    //Pasar las pantallas.
    private void pasarPantallaInicio() {
        if (panePantallaPantallaInicio == null){
            alerta.setContentText(NO_CARGAR_PANTALLA);
            alerta.showAndWait();
        }else {
            principal.setCenter(panePantallaPantallaInicio);
        }
    }

    private void pasarAddPantalla() {
        if (paneAddPantalla == null){
            alerta.setContentText(NO_CARGAR_PANTALLA);
            alerta.showAndWait();
        }else {
            principal.setCenter(paneAddPantalla);
        }
    }

    private void pasarDeletePantalla() {
        if (paneDeletePantalla == null) {
            alerta.setContentText(NO_CARGAR_PANTALLA);
            alerta.showAndWait();
        } else {
            principal.setCenter(paneDeletePantalla);
        }
    }

    private void pasarUpdatePantalla() {
        if (paneUpdatePantalla == null){
            alerta.setContentText(NO_CARGAR_PANTALLA);
            alerta.showAndWait();
        }else {
            principal.setCenter(paneUpdatePantalla);
        }
    }

    private void pasarViewPantalla() {
        if (paneViewPantalla == null){
            alerta.setContentText(NO_CARGAR_PANTALLA);
            alerta.showAndWait();
        }else {
            principal.setCenter(paneViewPantalla);
        }
    }

    //Los métodos para ir a las demás pantallas.
    @FXML
    private void volverInicio() {
        pasarPantallaInicio();
    }

    @FXML
    private void irAddPerson() {
        pasarAddPantalla();
    }

    @FXML
    private void irUpdatePerson() {
        updatePantalla.volcarInfo();
        pasarUpdatePantalla();
    }

    @FXML
    private void irViewPerson() {
        viewPantalla.volcarInfo();
        pasarViewPantalla();
    }

    @FXML
    private void irDeletePerson() {
        deletePantalla.volcarInfo();
        pasarDeletePantalla();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Al ser pocas pantallas, las cargo cuando se ejecute el programa y así, ya estarán cargadas.
        cargarInicio();
        cargarAddPantalla();
        cargarDelete();
        cargarUpdate();
        cargarView();
        pasarPantallaInicio();
    }
}
