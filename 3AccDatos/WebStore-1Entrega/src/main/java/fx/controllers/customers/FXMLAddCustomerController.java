/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fx.controllers.customers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Customer;
import services.CustomersServices;

/**
 * FXML Controller class
 *
 */
public class FXMLAddCustomerController implements Initializable {

    @FXML
    private Label lbCustomerId;
    @FXML
    private TextField idBox;
    @FXML
    private TextField nameBox;
    @FXML
    private TextField phoneBox;
    @FXML
    private TextField addressBox;
    @FXML
    private ListView<Customer> customerList;

    public void loadCustomersList() {
        CustomersServices cs = new CustomersServices();
        customerList.getItems().clear();
        customerList.getItems().addAll(cs.getAllCustomers());
     }

     @FXML
    private void addCustomer() {
        Alert alerta;
        CustomersServices cs = new CustomersServices();
        try{
            String name = nameBox.getText();
            String phone = phoneBox.getText();
            String adress = addressBox.getText();
            if(cs.addCustomer(name,phone,adress)){
                alerta = new Alert(AlertType.INFORMATION);
                alerta.setContentText("Customer Added successfully. ");
                alerta.showAndWait();
                loadCustomersList();
            }else{
                alerta = new Alert(AlertType.ERROR);
                alerta.setContentText("Customer couldn't be added.");
                alerta.showAndWait();
            }
        }catch (Exception e){
            Logger.getLogger("Error in AddCustomer (Controllers)").log(Level.WARNING,e.toString());
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadCustomersList();
        idBox.setVisible(false);
        lbCustomerId.setVisible(false);
    }

}
