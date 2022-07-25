package fx.controllers.purchases;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Purchase;
import services.PurchasesServices;
import utils.Constantes;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class FXMLUpdatePurchasesController implements Initializable {
    @FXML
    private TextField customerBox;
    @FXML
    private TextField itemBox;
    @FXML
    private ListView<Purchase> purchaseList;
    @FXML
    private DatePicker dateBox;


    public void load() {
        loadPurchasesList();
    }

    @FXML
    private void loadPurchasesList() {
        PurchasesServices ps = new PurchasesServices();
        purchaseList.getItems().clear();
        List<Purchase> list = ps.getAllPurchases();
        if(list != null) {
            purchaseList.getItems().addAll(list);
        }
    }


    @FXML
    private void updatePurchase() {
        PurchasesServices ps = new PurchasesServices();
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        if (purchaseList.getSelectionModel().getSelectedItem() != null) {
            Purchase p = purchaseList.getSelectionModel().getSelectedItem();
            LocalDate date = dateBox.getValue();
            p.setDate(date);
            ps.updatePurchase(p);
            purchaseList.getItems().set(purchaseList.getSelectionModel().getSelectedIndex(), p);
            alerta.setContentText(Constantes.PURCHASE_UPDATE_FINE);
            alerta.showAndWait();
            clean();
        } else {
            alerta.setContentText(Constantes.PURCHASE_NO_SELECTED);
            alerta.showAndWait();
        }
    }

    @FXML
    private void showPurchases() {
        if (purchaseList.getSelectionModel().getSelectedItem() != null) {
            customerBox.setText(purchaseList.getSelectionModel().getSelectedItem().getCustomer().toStringShort());
            itemBox.setText(purchaseList.getSelectionModel().getSelectedItem().getItem().toString());
            dateBox.setValue(purchaseList.getSelectionModel().getSelectedItem().getDate());
        }
    }

    private void clean() {
        customerBox.setText("");
        itemBox.setText("");
        dateBox.setValue(LocalDate.now());
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        customerBox.setEditable(false);
        itemBox.setEditable(false);
    }
}
