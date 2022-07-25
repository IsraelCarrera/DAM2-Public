package fx.controllers.customers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Customer;
import model.Purchase;
import services.CustomersServices;
import services.PurchasesServices;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class FXMLListCustomerController implements Initializable {
    CustomersServices cs = new CustomersServices();
    PurchasesServices ps = new PurchasesServices();

    @FXML
    private TextField tfName;
    @FXML
    private ListView<Customer> customerList;
    @FXML
    private TextField tfPhone;
    @FXML
    private TextField tfAddress;
    @FXML
    private ListView<String> lvPurchases;

    public void loadCustomer() {
        customerList.getItems().clear();
        List<Customer> list = cs.getAllCustomers();
        if(list != null) {
            customerList.getItems().addAll(list);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tfAddress.setEditable(false);
        tfPhone.setEditable(false);
        tfName.setEditable(false);
    }

    @FXML
    private void showInfo() {
        Customer customerSelected = customerList.getSelectionModel().getSelectedItem();
        if(customerSelected!= null){
            tfAddress.setText(customerSelected.getAddress());
            tfPhone.setText(customerSelected.getTelephone());
            tfName.setText(customerSelected.getName());
            lvPurchases.getItems().clear();

            List<Purchase> purchases = ps.getPurchasesByClientId(customerSelected.getIdCustomer());
            lvPurchases.getItems()
                    .addAll(purchases
                            .stream()
                            .map(p -> "Item name: " + p.getItem().getName() + " date: " + p.getDate())
                            .collect(Collectors.toList())
                    );
        }
    }
}
