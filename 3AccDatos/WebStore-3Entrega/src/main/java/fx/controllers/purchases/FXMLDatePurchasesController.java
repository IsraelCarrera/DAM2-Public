/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fx.controllers.purchases;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import model.Purchase;
import services.PurchasesServices;
import utils.Constantes;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;


/**
 * FXML Controller class
 *
 * @author Laura
 */
public class FXMLDatePurchasesController implements Initializable {

    @FXML
    private DatePicker dateBox;
    @FXML
    private ListView<Purchase> purchaseList;

    public void loadPurchasesList() {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void searchByDate() {
        Alert alerta;
        PurchasesServices ps = new PurchasesServices();
        LocalDate date = dateBox.getValue();
        purchaseList.getItems().clear();
        List<Purchase> purchases = ps.searchByDate(date);
        if (purchases != null) {
            purchaseList.getItems().addAll(purchases);
        } else {
            alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setContentText(Constantes.PURCHASE_NO_ID);
            alerta.showAndWait();
        }
    }
}
