/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fx.controllers.purchases;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import model.Purchase;
import services.PurchasesServices;
import services.ReviewsServices;
import utils.Constantes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author dam2
 */
public class FXMLDeletePurchasesController implements Initializable {

    @FXML
    private ListView<Purchase> purchaseBox;


    public void loadPurchases() {
        PurchasesServices ps = new PurchasesServices();
        purchaseBox.getItems().clear();
        List<Purchase> list = ps.getAllPurchases();
        if(list != null) {
            purchaseBox.getItems().addAll(list);
        }
    }

    public void deletePurchase() {
        Alert alerta;
        PurchasesServices ps = new PurchasesServices();
        ReviewsServices rs = new ReviewsServices();
        //If purchases has a Review, can't be deleted
        if (rs.reviewsByIdPurchase(purchaseBox.getSelectionModel().getSelectedItem().getIdPurchase()) ==null) {
            if (purchaseBox.getSelectionModel().getSelectedItem() != null) {
                if (ps.deletePurchase(purchaseBox.getSelectionModel().getSelectedItem())) {
                    purchaseBox.getItems().remove(purchaseBox.getSelectionModel().getSelectedItem());
                    alerta = new Alert(AlertType.INFORMATION);
                    alerta.setContentText(Constantes.PURCHASE_DELETE_FINE);
                } else {
                    alerta = new Alert(AlertType.ERROR);
                    alerta.setContentText(Constantes.PURCHASE_DELETE_WRONG);
                }
            } else {
                alerta = new Alert(AlertType.WARNING);
                alerta.setContentText(Constantes.PURCHASE_NO_SELECTED);
            }
        } else {
            alerta = new Alert(AlertType.ERROR);
            alerta.setContentText(Constantes.PURCHASE_NODELETE_REVIEWS);
        }
        alerta.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
