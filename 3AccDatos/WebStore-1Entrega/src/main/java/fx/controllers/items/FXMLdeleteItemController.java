package fx.controllers.items;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import model.Item;
import model.Purchase;
import services.ItemsServices;
import services.PurchasesServices;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FXMLdeleteItemController implements Initializable {
    @FXML
    private ListView<Item> itemsBox;

    private Alert alerta;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void deleteItem() {
        if(itemsBox.getSelectionModel().getSelectedItem() != null){
            ItemsServices is = new ItemsServices();
            PurchasesServices ps = new PurchasesServices();
            List<Purchase> purchaseList = ps.checkPurchaseByIdItem(itemsBox.getSelectionModel().getSelectedItem().getIdItem());
            if(purchaseList != null){
                alerta = new Alert(Alert.AlertType.CONFIRMATION);
                alerta.setContentText("This item has a purchase. Do you want to delete it anyway with their purchases?");
                alerta.showAndWait();
                if(alerta.getResult() == ButtonType.OK){
                    is.removeItem(itemsBox.getSelectionModel().getSelectedItem().getIdItem());
                    ps.deleteManyPurchases(purchaseList);
                    this.loadItemDelete();
                    alerta = new Alert(Alert.AlertType.INFORMATION);
                    alerta.setContentText("Item and its purchase has been deleted.");
                }else{
                    alerta = new Alert(Alert.AlertType.INFORMATION);
                    alerta.setContentText("Item hasn't been deleted.");
                }
                alerta.showAndWait();
                itemsBox.getSelectionModel().clearSelection();
            }else{
                if(is.removeItem(itemsBox.getSelectionModel().getSelectedItem().getIdItem())){
                    alerta = new Alert(Alert.AlertType.INFORMATION);
                    alerta.setContentText("Item has been deleted.");
                    this.loadItemDelete();
                }else{
                    alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setContentText("Items hasn't been deleted. Check all.");
                }
                alerta.showAndWait();
            }
        }else{
            alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setContentText("You don't choose any item to delete.");
            alerta.showAndWait();
        }
    }

    public void loadItemDelete() {
        ItemsServices servItems = new ItemsServices();
        itemsBox.getItems().clear();
        itemsBox.getItems().addAll(servItems.getAllItems());
    }
}
