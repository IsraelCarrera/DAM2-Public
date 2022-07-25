package fx.controllers.customers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Customer;
import model.Purchase;
import model.Review;
import services.CustomersServices;
import services.PurchasesServices;
import services.ReviewsServices;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class FXMLListCustomerController implements Initializable {
    CustomersServices cs = new CustomersServices();
    PurchasesServices ps = new PurchasesServices();
    ReviewsServices rs = new ReviewsServices();
    @FXML
    private Label lbIdPurchase;
    @FXML
    private Label lbItemName;
    @FXML
    private Label lbDate;
    @FXML
    private Label lbReview;
    @FXML
    private TextField tfName;
    @FXML
    private ListView<Customer> customerList;
    @FXML
    private TextField tfPhone;
    @FXML
    private TextField tfAddress;
    @FXML
    private ListView<Purchase> lvPurchases;

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
            tfPhone.setText(customerSelected.getPhone());
            tfName.setText(customerSelected.getName());
            lvPurchases.getItems().clear();
            clearInfoPurchase();
            List<Purchase> purchases = ps.getPurchasesByClientId(customerSelected.getDniCustomer());
            lvPurchases.getItems().addAll(purchases);
        }
    }

    @FXML
    private void showInfoPurchase() {
        if(lvPurchases.getSelectionModel().getSelectedItem()!=null){
            Purchase purchase = lvPurchases.getSelectionModel().getSelectedItem();
            lbDate.setText(purchase.getDate().toString());
            lbIdPurchase.setText(purchase.getIdPurchase());
            lbItemName.setText(purchase.getNameItem());
            //Buscar review.
            Review review = rs.reviewByPurchase(purchase);
            if(review!=null){
                lbReview.setText(review.toString());
            }else{
                lbReview.setText("there isn't review.");
            }
        }
    }

    private void clearInfoPurchase(){
        lbDate.setText("");
        lbIdPurchase.setText("");
        lbItemName.setText("");
        lbReview.setText("");
    }
}
