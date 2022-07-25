package fx.controllers.items;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import model.Item;
import model.Purchase;
import services.ItemsServices;
import services.PurchasesServices;
import services.ReviewsServices;
import utils.Constantes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FXMLdeleteItemController implements Initializable {
    @FXML
    private ListView<Item> itemsBox;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void deleteItem() {
        Alert alerta;
        if (itemsBox.getSelectionModel().getSelectedItem() != null) {
            ItemsServices is = new ItemsServices();
            PurchasesServices ps = new PurchasesServices();
            ReviewsServices rs = new ReviewsServices();
            List<Purchase> purchaseList = ps.checkPurchaseByIdItem(itemsBox.getSelectionModel().getSelectedItem().getIdItem());
            if (purchaseList!= null) {
                alerta = new Alert(Alert.AlertType.CONFIRMATION);
                alerta.setContentText(Constantes.ITEM_DELETE_WITHPURCHASES);
                alerta.showAndWait();
                if (alerta.getResult() == ButtonType.OK) {
                    //If any purchase has a review, cant delete this item, and this purchases.
                    if (rs.searchByItem(itemsBox.getSelectionModel().getSelectedItem().getIdItem())==null) {
                        is.removeItem(itemsBox.getSelectionModel().getSelectedItem(), purchaseList);
                        itemsBox.getItems().remove(itemsBox.getSelectionModel().getSelectedItem());
                        alerta = new Alert(Alert.AlertType.INFORMATION);
                        alerta.setContentText(Constantes.ITEM_PURCHASES_DELETE);
                    } else {
                        alerta = new Alert(Alert.AlertType.INFORMATION);
                        alerta.setContentText(Constantes.ITEM_DELETE_REVIEWS);
                    }
                } else {
                    alerta = new Alert(Alert.AlertType.INFORMATION);
                    alerta.setContentText(Constantes.ITEM_NODELETE);
                }
                itemsBox.getSelectionModel().clearSelection();
            } else {
                if (is.removeItem(itemsBox.getSelectionModel().getSelectedItem(), purchaseList)) {
                    alerta = new Alert(Alert.AlertType.INFORMATION);
                    alerta.setContentText(Constantes.ITEM_DELETE_FINE);
                    itemsBox.getItems().remove(itemsBox.getSelectionModel().getSelectedItem());
                } else {
                    alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setContentText(Constantes.ITEM_DELETE_WRONG);
                }
            }
            alerta.showAndWait();
        } else {
            alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setContentText(Constantes.ITEM_NO_SELECTED);
            alerta.showAndWait();
        }
    }

    public void loadItemDelete() {
        ItemsServices servItems = new ItemsServices();
        itemsBox.getItems().clear();
        List<Item> list = servItems.getAllItems();
        if(list != null) {
            itemsBox.getItems().addAll(list);
        }
    }
}
