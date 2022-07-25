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
import javafx.scene.control.ListView;
import model.Customer;
import services.CustomersServices;
import services.PurchasesServices;
import utils.Constantes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Laura
 */
public class FXMLdeleteCustomerController implements Initializable {

    @FXML
    private ListView<Customer> customerBox;

    public void loadCustomersList() {
        CustomersServices cs = new CustomersServices();
        customerBox.getItems().clear();
        List<Customer> list = cs.getAllCustomers();
        if (list != null) {
            customerBox.getItems().addAll(list);
        }
    }


    public void deleteCustomer() {
        Alert alerta;
        CustomersServices cs = new CustomersServices();
        if (customerBox.getSelectionModel().getSelectedItem() != null) {
            //Compruebo si tiene purchases
            if (!cs.customerHavePurchases(customerBox.getSelectionModel().getSelectedItem())) {
                //Si no las tiene, borro el customer.
                if (cs.deleteCustomer(customerBox.getSelectionModel().getSelectedItem().getDniCustomer())) {
                    alerta = new Alert(AlertType.INFORMATION);
                    alerta.setContentText(Constantes.CUSTOMER_DELETE_FINE);
                    customerBox.getItems().remove(customerBox.getSelectionModel().getSelectedItem());
                } else {
                    alerta = new Alert(AlertType.WARNING);
                    alerta.setContentText(Constantes.CUSTOMER_DELETE_WRONG);
                }
            } else {
                alerta = new Alert(AlertType.ERROR);
                alerta.setContentText(Constantes.CUSTOMER_DELETE_WITHPURCHASES);
            }
        } else {
            alerta = new Alert(AlertType.ERROR);
            alerta.setContentText(Constantes.CUSTOMER_NO_SELECTED);
        }
        alerta.showAndWait();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
