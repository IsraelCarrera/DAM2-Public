/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fx.controllers.reviews;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Customer;
import model.Purchase;
import model.Review;
import services.CustomersServices;
import services.PurchasesServices;
import services.ReviewsServices;

/**
 * FXML Controller class
 *
 * @author dam2
 */
public class FXMLAddReviewController implements Initializable {

    @FXML
    private ListView clientBox;
    @FXML
    private ListView purchaseBox;
    @FXML
    private ComboBox ratingBox;
    @FXML
    private TextField titleBox;
    @FXML
    private TextArea textBox;
    @FXML
    private ListView reviewList;

    public void loadCustomers() {
    }

     public void loadPurchases() {
     }

    public void addReview() {
            }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadCustomers();
    }

}
