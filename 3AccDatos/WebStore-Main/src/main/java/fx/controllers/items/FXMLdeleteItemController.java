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

    //REALIZAR MEJOR.
    //PRimero comprobamos si hay purchases (En items,con un count)
    //Si no hay purchases, se borra.
    //Si hay purchases, se compreuban las reviews con un count
    //Si hay reviews, no se borra, si no lo hay, si se borra.
    @FXML
    private void deleteItem() {
        Alert alerta;
        if (itemsBox.getSelectionModel().getSelectedItem() != null) {
            ItemsServices is = new ItemsServices();
            PurchasesServices ps = new PurchasesServices();
            //Comprobamos si tiene reviews.
            if (!is.itemHaveReviews(itemsBox.getSelectionModel().getSelectedItem())) {
                //Aquí, no tendría reviews. Entonces, comprobamos si tiene purchases y las cogemos.
                List<Purchase> purchases = ps.getPurchasesByItem(itemsBox.getSelectionModel().getSelectedItem());
                if (purchases.isEmpty()) {
                    is.removeItem(itemsBox.getSelectionModel().getSelectedItem());
                    itemsBox.getItems().remove(itemsBox.getSelectionModel().getSelectedItem());
                    alerta = new Alert(Alert.AlertType.INFORMATION);
                    alerta.setContentText(Constantes.ITEM_DELETE_FINE);
                } else {
                    //preguntamos si quiere borrar los items y las purchases.
                    alerta = new Alert(Alert.AlertType.CONFIRMATION);
                    alerta.setContentText(Constantes.ITEM_DELETE_WITHPURCHASES);
                    alerta.showAndWait();
                    if (alerta.getResult() == ButtonType.OK) {
                        ps.deletePurchasesOnlyInCustomer(purchases);
                        is.removeItem(itemsBox.getSelectionModel().getSelectedItem());
                        itemsBox.getItems().remove(itemsBox.getSelectionModel().getSelectedItem());
                        alerta = new Alert(Alert.AlertType.INFORMATION);
                        alerta.setContentText(Constantes.ITEM_PURCHASES_DELETE);
                    } else {
                        alerta = new Alert(Alert.AlertType.INFORMATION);
                        alerta.setContentText(Constantes.ITEM_NODELETE);
                    }
                }
            } else {
                alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setContentText(Constantes.ITEM_DELETE_REVIEWS);
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
        if (list != null) {
            itemsBox.getItems().addAll(list);
        }
    }
}
