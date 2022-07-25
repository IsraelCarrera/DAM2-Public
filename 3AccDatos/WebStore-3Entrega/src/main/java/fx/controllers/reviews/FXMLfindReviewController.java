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
import model.Item;
import model.Review;
import services.CustomersServices;
import services.ItemsServices;
import services.ReviewsServices;
import utils.Constantes;

/**
 * FXML Controller class
 *
 * @author dam2
 */
public class FXMLfindReviewController implements Initializable {

    @FXML
    private ListView<Review> reviewList;
    @FXML
    private ComboBox<Item> itemBox;

    private int userId;


    public void loadItems() {
        ItemsServices is = new ItemsServices();
        List<Item> list = is.getAllItems();
        itemBox.getItems().clear();
        if(list != null){
            itemBox.getItems().addAll(list);
        }
    }

    @FXML
    private void searchByItem() {
        Alert alert;
        if(itemBox.getSelectionModel().getSelectedItem() != null) {
            reviewList.getItems().clear();
            int idItem =itemBox.getSelectionModel().getSelectedItem().getIdItem();
                    List < Review > list;
            if (userId <= 0) {
                list=isAdmin(idItem);
            } else {
                list= isUser(idItem);
            }
            if(list!= null) {
                reviewList.getItems().addAll(list);
            }else{
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(Constantes.REVIEWS_NOITEM);
                alert.showAndWait();
            }
        }else{
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(Constantes.REVIEWS_NOTSELECTED);
            alert.showAndWait();
        }

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void getId(int id){
        userId=id;
    }

    private List<Review> isAdmin(int idItem){
        ReviewsServices rs = new ReviewsServices();
        return rs.searchByItem(idItem);
    }
    private List<Review> isUser(int idItem){
        ReviewsServices rs = new ReviewsServices();
        return rs.reviewsByIdItemAndCustomer(idItem,userId);
    }
}
