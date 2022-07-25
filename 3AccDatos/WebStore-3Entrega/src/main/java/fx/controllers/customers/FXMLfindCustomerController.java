/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fx.controllers.customers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Customer;
import services.CustomersServices;
import utils.Constantes;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Laura
 */
public class FXMLfindCustomerController implements Initializable {

    @FXML
    private TextField idBox;
    @FXML
    private ListView<Customer> customerList;

    public void searchById() {
        Alert alerta;
        CustomersServices cs = new CustomersServices();
        try {
            int id = Integer.parseInt(idBox.getText());
            Customer customer = cs.searchById(id);
            if (customer != null) {
                customerList.getItems().clear();
                customerList.getItems().add(customer);
            } else {
                alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setContentText(Constantes.CUSTOMER_NO_ID);
                alerta.showAndWait();
            }
        } catch (Exception e) {
            alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText(Constantes.WRITE_NUMBER);
            alerta.showAndWait();
        }
    }


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
