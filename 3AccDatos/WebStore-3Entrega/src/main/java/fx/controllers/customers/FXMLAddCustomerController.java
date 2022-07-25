/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fx.controllers.customers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Customer;
import model.User;
import services.CustomersServices;
import utils.Constantes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 */
public class FXMLAddCustomerController implements Initializable {

    @FXML
    private TextField tfUsername;
    @FXML
    private TextField tfPassword;
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
        List<Customer> list = cs.getAllCustomers();
        if(list != null) {
            customerList.getItems().addAll(list);
        }
    }

    @FXML
    private void addCustomer() {
        Alert alerta;
        CustomersServices cs = new CustomersServices();
        try {
            String username = tfUsername.getText();
            String password = tfPassword.getText();
            String name = nameBox.getText();
            String phone = phoneBox.getText();
            String adress = addressBox.getText();
            User user = new User(username,password);
            Customer customer = new Customer(name, phone, adress);
            int idCustomer =cs.addCustomer(customer, user);
            if(idCustomer == -1){
                alerta = new Alert(AlertType.ERROR);
                alerta.setContentText(Constantes.CUSTOMER_EXISTS);
                alerta.showAndWait();
            }
            else if (idCustomer !=0) {
                alerta = new Alert(AlertType.INFORMATION);
                alerta.setContentText(Constantes.CUSTOMER_ADD_FINE);
                alerta.showAndWait();
                customer.setIdCustomer(idCustomer);
                customerList.getItems().add(customer);
            } else {
                alerta = new Alert(AlertType.ERROR);
                alerta.setContentText(Constantes.CUSTOMER_ADD_WRONG);
                alerta.showAndWait();
            }
        } catch (Exception e) {
            Logger.getLogger(FXMLAddCustomerController.class.getName()).log(Level.SEVERE, e.getMessage(), e);

        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
