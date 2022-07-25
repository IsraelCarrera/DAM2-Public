/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fx.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Laura
 */
public class FXMLWelcomeController implements Initializable {

    @FXML
    private Label fxWelcomeTitle;
    
    @FXML
    private AnchorPane fxWelcomePane;

    // String con el nombre de usuario que le paso desde el login.
    private String login;
    
    public String getLogin() {
        return login;
    }
    
    //Acceso a la principal, y otras. (No necesario en menu, pero para botones?)
    
    private FXMLPrincipalController principal;
    private FXMLLoginController loginPane;
    
    

    public void setLogin(String login) {
        this.login = login;
        fxWelcomeTitle.setText("Welcome " + login);
    }
    
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //fxWelcomeTitle.setText("Welcome back " + login);
    }    
    
}
