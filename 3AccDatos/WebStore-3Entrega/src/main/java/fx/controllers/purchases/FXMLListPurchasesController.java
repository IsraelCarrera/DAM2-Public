package fx.controllers.purchases;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import model.Purchase;
import services.PurchasesServices;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FXMLListPurchasesController implements Initializable {

    private final PurchasesServices ps = new PurchasesServices();

    @FXML
    private ListView<Purchase> purchasesList;

    public void loadPurchaseList() {
        purchasesList.getItems().clear();
        List<Purchase> list = ps.getAllPurchases();
        if(list != null) {
            purchasesList.getItems().addAll(list);
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
