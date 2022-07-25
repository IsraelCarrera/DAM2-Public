/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fx.controllers.purchases;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import model.Customer;
import model.Item;
import model.Purchase;
import services.CustomersServices;
import services.ItemsServices;
import services.PurchasesServices;
import utils.Constantes;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Laura
 */
public class FXMLAddPurchasesController implements Initializable {


    @FXML
    private ComboBox<Customer> customerBox;
    @FXML
    private ComboBox<Item> itemBox;
    @FXML
    private ListView<Purchase> purchaseList;
    @FXML
    private DatePicker dateBox;


    public void load() {
        loadItemsList();
        loadCustomersList();
        loadPurchasesList();
    }

    @FXML
    private void loadPurchasesList() {
        PurchasesServices ps = new PurchasesServices();
        purchaseList.getItems().clear();
        purchaseList.getItems().addAll(ps.getAllPurchases());
    }

    @FXML
    private void loadItemsList() {
        ItemsServices is = new ItemsServices();
        itemBox.getItems().clear();
        itemBox.getItems().addAll(is.getAllItems());

    }

    @FXML
    private void loadCustomersList() {
        CustomersServices cs = new CustomersServices();
        customerBox.getItems().clear();
        customerBox.getItems().addAll(cs.getAllCustomers());
    }

    @FXML
    private void addPurchase() {
        PurchasesServices ps = new PurchasesServices();
        Alert alerta;
        if (!customerBox.getSelectionModel().isEmpty() && !itemBox.getSelectionModel().isEmpty()) {
            LocalDate datePurchase = dateBox.getValue();
            Customer customer = customerBox.getSelectionModel().getSelectedItem();
            Item item = itemBox.getSelectionModel().getSelectedItem();
            Purchase purchase = new Purchase(customer, item, datePurchase);
            if (ps.addPurchase(purchase)) {
                purchaseList.getItems().add(purchase);
                alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setContentText(Constantes.PURCHASE_ADD_FINE);
            } else {
                alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setContentText(Constantes.PURCHASE_ADD_WRONG);
            }
        } else {
            alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setContentText(Constantes.PURCHASE_ADD_NOSELECTED);
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
