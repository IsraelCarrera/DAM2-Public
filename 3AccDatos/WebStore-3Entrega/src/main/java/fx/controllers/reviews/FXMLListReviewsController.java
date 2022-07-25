package fx.controllers.reviews;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import model.Review;
import services.ReviewsServices;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FXMLListReviewsController implements Initializable {

    private int idUser;
    @FXML
    private ListView<Review> reviewList;

    public void loadReviews() {
        List<Review> list;
        reviewList.getItems().clear();
        if(idUser<=0){
            list = isAdmin();
        }else{
            list = isUser();
        }
        if(list != null) {
            reviewList.getItems().addAll(list);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    private List<Review> isUser(){
        ReviewsServices rs = new ReviewsServices();
        return rs.reviewsByIdCustomer(idUser);
    }

    private List<Review> isAdmin(){
        ReviewsServices rs = new ReviewsServices();
        return rs.getAllReviews();
    }

    public void getId(int id){
        idUser = id;
    }
}
