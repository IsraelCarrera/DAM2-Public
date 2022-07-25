package fx.controllers.reviews;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Customer;
import model.Item;
import model.Review;
import services.CustomersServices;
import services.ItemsServices;
import services.ReviewsServices;
import utils.Constantes;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FXMLListReviewsController implements Initializable {

    ReviewsServices rs = new ReviewsServices();
    CustomersServices cs = new CustomersServices();
    ItemsServices is = new ItemsServices();
    private int idUser;
    @FXML
    private ComboBox<String> cbChoose;
    @FXML
    private ComboBox<Item> cbItem;
    @FXML
    private ComboBox<Customer> cbCustomer;
    @FXML
    private ComboBox<Integer> cbRating;
    @FXML
    private Label lbItem;
    @FXML
    private Label lbCustomer;
    @FXML
    private Label lbRating;
    @FXML
    private ListView<Review> reviewList;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        invisibility();
        if (idUser <= 0) {
            //Si es admin
            cbChoose.getItems().addAll(Constantes.ALL, Constantes.ITEMS, Constantes.CUSTOMERS, Constantes.RATING);
            cbChoose.setValue(Constantes.ALL);
            cbRating.getItems().addAll(1, 2, 3, 4, 5);
            cbRating.setValue(1);
            cbCustomer.getItems().addAll(cs.getAllCustomers());
            cbItem.getItems().addAll(is.getAllItems());
        } else {
            //Si no lo es
            cbChoose.setValue(Constantes.ALL);
        }
    }


    public void getId(int id) {
        idUser = id;
    }


    @FXML
    private void cbShowChoose() {
        switch (cbChoose.getSelectionModel().getSelectedItem()) {
            case Constantes.ITEMS:
                visibilityItems();
                break;
            case Constantes.CUSTOMERS:
                visibilityCustomer();
                break;
            case Constantes.RATING:
                visibilityRating();
                break;
            default:
                invisibility();
                break;
        }
    }

    private void visibilityRating() {
        lbRating.setVisible(true);
        cbRating.setVisible(true);
        lbItem.setVisible(false);
        cbItem.setVisible(false);
        lbCustomer.setVisible(false);
        cbCustomer.setVisible(false);
    }

    private void visibilityCustomer() {
        lbRating.setVisible(false);
        cbRating.setVisible(false);
        lbItem.setVisible(false);
        cbItem.setVisible(false);
        lbCustomer.setVisible(true);
        cbCustomer.setVisible(true);
    }

    private void visibilityItems() {
        lbRating.setVisible(false);
        cbRating.setVisible(false);
        lbItem.setVisible(true);
        cbItem.setVisible(true);
        lbCustomer.setVisible(false);
        cbCustomer.setVisible(false);
    }

    private void invisibility() {
        lbItem.setVisible(false);
        lbCustomer.setVisible(false);
        lbRating.setVisible(false);
        cbItem.setVisible(false);
        cbCustomer.setVisible(false);
        cbRating.setVisible(false);
    }

    @FXML
    private void btnSearch() {
        switch (cbChoose.getSelectionModel().getSelectedItem()) {
            case Constantes.ITEMS:
                if (cbItem.getSelectionModel().getSelectedItem() != null) {
                    searchItems(cbItem.getSelectionModel().getSelectedItem().getIdItem());
                } else {
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setContentText(Constantes.YOU_HAVE_NOT_SELECTED_ANYTHING);
                    alerta.showAndWait();
                }
                break;
            case Constantes.CUSTOMERS:
                if (cbCustomer.getSelectionModel().getSelectedItem() != null) {
                    searchCustomer(cbCustomer.getSelectionModel().getSelectedItem().getIdCustomer());
                } else {
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setContentText(Constantes.YOU_HAVE_NOT_SELECTED_ANYTHING);
                    alerta.showAndWait();
                }
                break;
            case Constantes.RATING:
                searchRating(cbRating.getSelectionModel().getSelectedItem());
                break;
            default:
                if (idUser <= 0) {
                    searchAll();
                } else {
                    searchCustomer(idUser);
                }
                break;
        }
    }

    private void searchAll() {
        reviewList.getItems().clear();
        List<Review> list = rs.getAllReviews();
        if (!list.isEmpty()) {
            reviewList.getItems().addAll(list);
        } else {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText(Constantes.NO_DATA);
            alerta.showAndWait();
        }

    }

    private void searchRating(int rate) {
        reviewList.getItems().clear();
        List<Review> list = rs.reviewsByRate(rate);
        if (!list.isEmpty()) {
            reviewList.getItems().addAll(list);
        } else {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText(Constantes.NO_DATA);
            alerta.showAndWait();
        }
    }

    private void searchCustomer(int idCustomer) {
        reviewList.getItems().clear();
        List<Review> list = rs.reviewsByIdCustomer(idCustomer);
        if (!list.isEmpty()) {
            reviewList.getItems().addAll(list);
        } else {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText(Constantes.NO_DATA);
            alerta.showAndWait();
        }
    }

    private void searchItems(int idItem) {
        reviewList.getItems().clear();
        List<Review> list = rs.searchByItem(idItem);
        if (!list.isEmpty()) {
            reviewList.getItems().addAll(list);
        } else {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText(Constantes.NO_DATA);
            alerta.showAndWait();
        }
    }

    @FXML
    private void moreInfo() {
        if (reviewList.getSelectionModel().getSelectedItem() != null) {
            try {
                Stage stage = new Stage();
                FXMLLoader loaderMenu = new FXMLLoader(
                        getClass().getResource(
                                "/fxml/reviews/FXMLMoreInfoReviews.fxml"));
                stage.setScene(new Scene(loaderMenu.load()));
                FXMLMoreInfoReviews fxmlMoreInfoReviews = loaderMenu.getController();
                fxmlMoreInfoReviews.cargaDatos(reviewList.getSelectionModel().getSelectedItem());
                stage.setTitle(Constantes.MORE_INFO_REVIEW);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(FXMLListReviewsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
