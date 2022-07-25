package fx.controllers.reviews;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Purchase;
import model.Review;
import services.ReviewsServices;
import utils.Constantes;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class FXMLUpdateReviewController implements Initializable {
    @FXML
    private TextArea textBox;
    @FXML
    private ListView<Review> reviewBox;
    @FXML
    private ComboBox<Integer> ratingBox;
    @FXML
    private TextField titleBox;

    private int userId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void updateReview() {
        Alert alert;
        ReviewsServices rs = new ReviewsServices();
        try {
            if (reviewBox.getSelectionModel().getSelectedItem() != null) {
                int idReview = reviewBox.getSelectionModel().getSelectedItem().getIdReview();
                int rating = ratingBox.getValue();
                String title = titleBox.getText();
                String descr = textBox.getText();
                Purchase p = reviewBox.getSelectionModel().getSelectedItem().getPurchase();
                if(!title.isEmpty() && !descr.isEmpty()) {
                    Review review = new Review(idReview,rating, title, descr, LocalDate.now(), p);
                    boolean update= rs.updateReview(review);
                    if (update) {
                        reviewBox.getItems().set(reviewBox.getSelectionModel().getSelectedIndex(),review);
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText(Constantes.REVIEW_UPDATE);
                    } else {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText(Constantes.REVIEW_NOUPDATE);
                    }
                }else{
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText(Constantes.REVIEW_MISSING_TD);
                }
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(Constantes.REVIEWS_NOTSELECTED);
            }
            alert.showAndWait();
        }catch (Exception e){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(Constantes.REVIEW_NORATING);
            alert.showAndWait();
        }
    }

    @FXML
    private void printDates() {
        if(reviewBox.getSelectionModel().getSelectedItem()!=null){
            Review r = reviewBox.getSelectionModel().getSelectedItem();
            ratingBox.getSelectionModel().select(r.getRating()-1);
            titleBox.setText(r.getTitle());
            textBox.setText(r.getDescription());
        }
    }


    public void loadUpdateReviews() {
        List<Review> list;
        reviewBox.getItems().clear();
        if(userId<=0){
            list = isAdmin();
        }else{
            list = isUser();
        }
        if(list != null) {
            reviewBox.getItems().addAll(list);
        }
        ratingBox.getItems().clear();
        ratingBox.getItems().addAll(1,2,3,4,5);
    }

    public void getId(int idUser) {
        userId = idUser;
    }

    private List<Review> isUser(){
        ReviewsServices rs = new ReviewsServices();
        return rs.reviewsByIdCustomer(userId);
    }

    private List<Review> isAdmin(){
        ReviewsServices rs = new ReviewsServices();
        return rs.getAllReviews();
    }
}
