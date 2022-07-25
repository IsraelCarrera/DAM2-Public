/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fx.controllers.reviews;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import model.Customer;
import model.Review;
import services.CustomersServices;
import services.ReviewsServices;
import utils.Constantes;

/**
 * FXML Controller class
 *
 * @author dam2
 */
public class FXMLdeleteReviewController implements Initializable {

    @FXML
    private ListView<Customer> customerBox;
    @FXML
    private ListView<Review> reviewBox;

    @FXML
    private void loadReviewsList() {
        ReviewsServices rs = new ReviewsServices();
        List<Review> list = rs.reviewsByIdCustomer(customerBox.getSelectionModel().getSelectedItem().getIdCustomer());
        reviewBox.getItems().clear();
        if(list != null){
            reviewBox.getItems().addAll(list);
        }
     }

    public void loadCustomersList() {
        CustomersServices cs = new CustomersServices();
        List<Customer> list = cs.getAllCustomers();
        customerBox.getItems().clear();
        if(list != null){
            customerBox.getItems().addAll(list);
        }
    }

    @FXML
    private void deleteReview() {
        Alert alert;
        ReviewsServices rs = new ReviewsServices();
        if(reviewBox.getSelectionModel().getSelectedItem() != null){
            if(rs.deleteReview(reviewBox.getSelectionModel().getSelectedItem())){
                reviewBox.getItems().remove(reviewBox.getSelectionModel().getSelectedItem());
                alert = new Alert(AlertType.INFORMATION);
                alert.setContentText(Constantes.REVIEW_REMOVED_SUCCESSFULLY);
            }else{
                alert = new Alert(AlertType.ERROR);
                alert.setContentText(Constantes.REVIEW_NOT_DELETED);
            }
        }else{
            alert = new Alert(AlertType.ERROR);
            alert.setContentText(Constantes.REVIEWS_NOTSELECTED);
        }
        alert.showAndWait();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
