package fx.controllers.items;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Item;
import services.ItemsServices;
import utils.Constantes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FXMLAddItemController implements Initializable {

    @FXML
    private Label lbId;
    @FXML
    private ListView<Item> itemList;
    @FXML
    private TextField idBox;
    @FXML
    private TextField nameBox;
    @FXML
    private TextField companyBox;
    @FXML
    private TextField priceBox;

    @FXML
    private void addItem() {
        Alert alerta;
        ItemsServices is = new ItemsServices();
        try {
            String name = nameBox.getText();
            String company = companyBox.getText();
            double price = (double)Math.round(Double.parseDouble(priceBox.getText()) * 100d) / 100d;
            Item item = new Item(name, company, price);
            int id= is.addItem(item);
            if(id != 0) {
                item.setIdItem(id);
                itemList.getItems().add(item);
                alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setContentText(Constantes.ITEM_ADD_FINE);
            }else{
                alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setContentText(Constantes.ITEM_ADD_WRONG);
            }
        } catch (Exception e) {
            alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText(Constantes.ITEM_ADD_WRONG);
        }
        alerta.showAndWait();
    }

    public void loadItems() {
        ItemsServices servItems = new ItemsServices();
        itemList.getItems().clear();
        List<Item> list = servItems.getAllItems();
        if(list != null) {
            itemList.getItems().addAll(list);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idBox.setVisible(false);
        lbId.setVisible(false);
    }
}
