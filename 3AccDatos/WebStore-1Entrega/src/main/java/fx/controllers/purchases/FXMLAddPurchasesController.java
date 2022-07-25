/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fx.controllers.purchases;


import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
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
        if(!customerBox.getSelectionModel().isEmpty() && !itemBox.getSelectionModel().isEmpty()){
            LocalDate datePurchase = dateBox.getValue();
            int idCustomer = customerBox.getSelectionModel().getSelectedItem().getIdCustomer();
            int idItem = itemBox.getSelectionModel().getSelectedItem().getIdItem();
            if(ps.addPurchase(idCustomer,idItem,datePurchase)){
                loadPurchasesList();
            }else{
                alerta= new Alert(Alert.AlertType.ERROR);
                alerta.setContentText("The purchase could not be added.");
                alerta.showAndWait();
            }
        }else{
            alerta= new Alert(Alert.AlertType.ERROR);
            alerta.setContentText("You haven't selected an item or customer.");
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
