/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fx.controllers.reviews;

import java.net.URL;
import java.time.LocalDate;
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
import utils.Constantes;

/**
 * FXML Controller class
 *
 * @author dam2
 */
public class FXMLAddReviewController implements Initializable {


    private int idUser;
    @FXML
    private ListView<Customer> clientBox;
    @FXML
    private ListView<Purchase> purchaseBox;
    @FXML
    private ComboBox<Integer> ratingBox;
    @FXML
    private TextField titleBox;
    @FXML
    private TextArea textBox;


    public void loadCustomer() {
        purchaseBox.getItems().clear();
        if(idUser<=0){
            loadAdmin();
        }else{
            loadClient(idUser);
        }
        ratingBox.getItems().clear();
        ratingBox.getItems().addAll(1,2,3,4,5);
    }


    public void getId(int id){
        idUser = id;
    }

    @FXML
    private void loadPurchases() {
        PurchasesServices ps = new PurchasesServices();
        purchaseBox.getItems().clear();
        List<Purchase> list = ps.getPurchasesByClientId(clientBox.getSelectionModel().getSelectedItem().getIdCustomer());
        if(list !=  null) {
            purchaseBox.getItems().addAll(list);
        }
     }

    @FXML
    private void addReview() {
        Alert alert;
        ReviewsServices rs = new ReviewsServices();
        try {
            if (purchaseBox.getSelectionModel().getSelectedItem() != null && clientBox.getSelectionModel().getSelectedItem() != null) {
                Purchase p = purchaseBox.getSelectionModel().getSelectedItem();
                int rating = ratingBox.getValue();
                String title = titleBox.getText();
                String descr = textBox.getText();
                if(!title.isEmpty() && !descr.isEmpty()) {
                    Review review = new Review(rating, title, descr, LocalDate.now(), p);
                    int idReview = rs.addReview(review);
                    if (idReview > 0) {
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText(Constantes.REVIEW_ADD_SUCCESSFULLY);
                    } else {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText(Constantes.REVIEW_NOT_SAVED);
                    }
                }else{
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText(Constantes.REVIEW_MISSING_TD);
                }
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(Constantes.REVIEW_DONTSELECTED);
            }
            alert.showAndWait();
        }catch (Exception e){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(Constantes.REVIEW_NORATING);
            alert.showAndWait();
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    private void loadClient(int idUser){
        CustomersServices cs = new CustomersServices();
        clientBox.getItems().clear();
        Customer c = cs.searchById(idUser);
        if(c != null) {
            clientBox.getItems().add(c);
        }
    }

    private void loadAdmin() {
        CustomersServices cs = new CustomersServices();
        clientBox.getItems().clear();
        List<Customer> list = cs.getAllCustomers();
        if(list != null) {
            clientBox.getItems().addAll(cs.getAllCustomers());
        }
    }
}
