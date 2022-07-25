/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.User;
import services.UsersServices;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Laura
 */
public class FXMLLoginController implements Initializable {


    // Esto es para poder coger lo que pone en ese campo y meterlo en este caso en el atributo usuario
    // del controlador principal.
    @FXML
    private TextField fxUser;
    @FXML
    private TextField passBox;
    @FXML
    private Label errorBox;
    private User user;

    //Referencia al controlador principal para poder acceder a el, junto con su set para poder cambiarlo.
    private FXMLPrincipalController principal;

    public void setPrincipal(FXMLPrincipalController principal) {
        this.principal = principal;
    }


    public void clickLogin() {
        UsersServices us = new UsersServices();
        User userTest = new User(fxUser.getText(),passBox.getText());
        user = us.userLoggin(fxUser.getText(),passBox.getText());
        if (user.equals(userTest)) {
            principal.setUsername(fxUser.getText());
            principal.chargeWelcome();
        } else {
            errorBox.setText("User or password is wrong");
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public int getId() {
        return user.getIdUser();
    }
}
