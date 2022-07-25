package fx.controllers.customers;

import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import model.Customer;
import services.CustomersServices;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLListCustomerController implements Initializable {


    public ListView<Customer> customerList;

    public void loadCustomer() {
        CustomersServices cs = new CustomersServices();
        customerList.getItems().clear();
        customerList.getItems().addAll(cs.getAllCustomers());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
