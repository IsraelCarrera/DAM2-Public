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

public class FXMLUpdateCustomerController implements Initializable {
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

    //Modify
    @FXML
    private void updateCustomer() {
        Alert alerta;
        if (customerList.getSelectionModel().getSelectedItem() != null) {
            CustomersServices cs = new CustomersServices();
            try {
                int id = Integer.parseInt(idBox.getText());
                String name = nameBox.getText();
                String phone = phoneBox.getText();
                String adress = addressBox.getText();
                Customer customer = new Customer(id, name, phone, adress);
                cs.updateCustomer(customer);
                customerList.getItems().set(customerList.getSelectionModel().getSelectedIndex(), customer);
                alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setContentText(Constantes.CUSTOMER_UPDATE_FINE);
            } catch (Exception e) {
                alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setContentText(Constantes.CUSTOMER_UPDATE_WRONG);
            } finally {
                clean();
            }
        } else {
            alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setContentText(Constantes.CUSTOMER_NO_SELECTED);
        }
        alerta.showAndWait();
    }

    @FXML
    private void showCustomer() {
        if (customerList.getSelectionModel().getSelectedItem() != null) {
            idBox.setText(String.valueOf(customerList.getSelectionModel().getSelectedItem().getIdCustomer()));
            nameBox.setText(customerList.getSelectionModel().getSelectedItem().getName());
            phoneBox.setText(customerList.getSelectionModel().getSelectedItem().getPhone());
            addressBox.setText(customerList.getSelectionModel().getSelectedItem().getAddress());
        }
    }

    private void clean() {
        idBox.setText("");
        nameBox.setText("");
        phoneBox.setText("");
        addressBox.setText("");

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadCustomersList();
        idBox.setEditable(false);
    }
}
