/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fx.controllers.purchases;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import services.PurchasesServices;
import model.Purchase;

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
        purchaseBox.getItems().addAll(ps.getAllPurchases());
     }
    
    public void deletePurchase(){
        Alert alerta;
        PurchasesServices ps = new PurchasesServices();
        if(purchaseBox.getSelectionModel().getSelectedItem() != null){
            if(ps.deletePurchase(purchaseBox.getSelectionModel().getSelectedItem())){
                loadPurchases();
                alerta = new Alert(AlertType.INFORMATION);
                alerta.setContentText("Purchase deleted with success");
            }else{
                alerta = new Alert(AlertType.ERROR);
                alerta.setContentText("Can't delete purchase.");
            }
        }else{
            alerta = new Alert(AlertType.WARNING);
            alerta.setContentText("Any selected.");
        }
        alerta.showAndWait();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadPurchases();
    }

}
