/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fx.controllers.items;

import fx.controllers.reviews.FXMLListReviewsController;
import fx.controllers.reviews.FXMLMoreInfoReviews;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Item;
import model.Review;
import services.ItemsServices;
import services.PurchasesServices;
import services.ReviewsServices;
import utils.Constantes;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * FXML Controller class
 */
public class FXMLListItemsController implements Initializable {

    private final ItemsServices is = new ItemsServices();
    private final PurchasesServices ps = new PurchasesServices();
    private final ReviewsServices rs = new ReviewsServices();

    //Para poder buscar con más info la review, necesito como auxiliar una lista de Reviews, ya que al pintar solo se pintará Title y Rating.

    private final List<Review> reviewList = new ArrayList<>();
    @FXML
    private ListView<String> lvReviews;
    @FXML
    private Label lbInfoMesPurchases;
    @FXML
    private ComboBox<String> cbListReviews;
    @FXML
    private TextField tfPrice;
    @FXML
    private TextField tfNumberPurchases;
    @FXML
    private TextField tfAverage;
    @FXML
    private ListView<Item> itemsList;

    public void loadItemsList() {
        itemsList.getItems().clear();
        List<Item> list = is.getAllItems();
        if (list != null) {
            itemsList.getItems()
                    .addAll(list);
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbListReviews.getItems().addAll(Constantes.DATE_ASCENDANT, Constantes.DATE_DESCENDENT, Constantes.RATING_ASCENDANT, Constantes.RATING_DESCENDENT);
        cbListReviews.setValue(Constantes.DATE_ASCENDANT);
        tfAverage.setEditable(false);
        tfPrice.setEditable(false);
        tfNumberPurchases.setEditable(false);
        lbInfoMesPurchases.setText(Constantes.NUMBER_OF_PURCHASES_IN + LocalDate.now().minusMonths(1).getMonth().toString() + ":");
    }

    @FXML
    private void moreInfo() {
        Item itemSelected = itemsList.getSelectionModel().getSelectedItem();
        if (itemSelected != null) {
            //Price
            tfPrice.setText(String.valueOf(itemSelected.getPrice()));
            //Reviews
            List<Review> reviews = rs.searchByItem(itemSelected.getIdItem());
            shortList(reviews);
            //Purchases in month
            tfNumberPurchases.setText(String.valueOf(ps.getPurchasesInMonth(itemSelected.getIdItem())));
            //AVG Rating
            double avgRating = rs.avgRatingByIdItem(itemSelected.getIdItem());
            if (avgRating != -1) {
                tfAverage.setText(String.valueOf(avgRating));
            } else {
                tfAverage.setText(Constantes.THERE_IS_NOT_ENOUGH_RATE);
            }
        }
    }

    @FXML
    private void viewInfoReviews() {
        if (lvReviews.getSelectionModel().getSelectedItem() != null) {
            try {
                Stage stage = new Stage();
                FXMLLoader loaderMenu = new FXMLLoader(
                        getClass().getResource(
                                "/fxml/reviews/FXMLMoreInfoReviews.fxml"));
                stage.setScene(new Scene(loaderMenu.load()));
                FXMLMoreInfoReviews fxmlMoreInfoReviews = loaderMenu.getController();
                fxmlMoreInfoReviews.cargaDatos(reviewList.get(lvReviews.getSelectionModel().getSelectedIndex()));
                stage.setTitle(Constantes.MORE_INFO_REVIEW);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(FXMLListReviewsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void listByChoose() {
        Item itemSelected = itemsList.getSelectionModel().getSelectedItem();
        if (itemSelected != null) {
            switch (cbListReviews.getSelectionModel().getSelectedItem()) {
                case Constantes.DATE_ASCENDANT: {
                    List<Review> reviews = rs.reviewsByIdItemOrderByDateAsc(itemSelected.getIdItem());
                    shortList(reviews);
                    break;
                }
                case Constantes.DATE_DESCENDENT: {
                    List<Review> reviews = rs.reviewsByIdItemOrderByDateDesc(itemSelected.getIdItem());
                    shortList(reviews);
                    break;
                }
                case Constantes.RATING_ASCENDANT: {
                    List<Review> reviews = rs.reviewsByIdItemOrderByRatingAsc(itemSelected.getIdItem());
                    shortList(reviews);
                    break;
                }
                case Constantes.RATING_DESCENDENT: {
                    List<Review> reviews = rs.reviewsByIdItemOrderByRatingDesc(itemSelected.getIdItem());
                    shortList(reviews);
                    break;
                }
            }
        }
    }

    private void shortList(List<Review> reviews) {
        lvReviews.getItems().clear();
        reviewList.clear();
        if (!reviews.isEmpty()) {
            reviewList.addAll(reviews);
            lvReviews.getItems()
                    .addAll(reviewList
                            .stream()
                            .map(review -> {
                                return "title: " + review.getTitle() + " rating: " + review.getRating();
                            })
                            .collect(Collectors.toList())
                    );
        }
    }
}
