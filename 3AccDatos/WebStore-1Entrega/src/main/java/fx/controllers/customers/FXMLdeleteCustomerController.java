/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fx.controllers.customers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import model.Customer;
import services.CustomersServices;

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
        customerBox.getItems().addAll(cs.getAllCustomers());
    }
    
    public void deleteCustomer() {
        Alert alerta;
        CustomersServices cs = new CustomersServices();
        if(customerBox.getSelectionModel().getSelectedItem()!=null){
            if(cs.deleteCustomer(customerBox.getSelectionModel().getSelectedItem())){
                alerta = new Alert(AlertType.INFORMATION);
                alerta.setContentText("Customer delete with success.");
                loadCustomersList();
            }else{
                alerta = new Alert(AlertType.WARNING);
                alerta.setContentText("Customer can't be deleted.");
            }
        }else{
            alerta = new Alert(AlertType.ERROR);
            alerta.setContentText("You don't choose any customer.");
        }
        alerta.showAndWait();
        }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadCustomersList();
    }

}
