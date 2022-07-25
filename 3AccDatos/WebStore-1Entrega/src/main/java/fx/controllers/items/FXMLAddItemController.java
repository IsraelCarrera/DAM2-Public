package fx.controllers.items;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Item;
import services.ItemsServices;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLAddItemController implements Initializable {

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

    Alert alerta = new Alert(Alert.AlertType.WARNING);
    @FXML
    private void addItem() {
        ItemsServices is = new ItemsServices();
        try {
            int id = Integer.parseInt(idBox.getText());
            String name = nameBox.getText();
            String company = companyBox.getText();
            double price = Double.parseDouble(priceBox.getText());
            if (is.itsId(id)) {
                alerta.setContentText("The ID is in the list of items.");
                alerta.showAndWait();
            } else {
                if(is.addItem(id, name, company, price)) {
                    itemList.getItems().clear();
                    itemList.getItems().addAll(is.getAllItems());
                }else{
                    alerta.setContentText("Can't add item. Write all data.");
                    alerta.showAndWait();
                }
            }
        }catch (Exception e){
            alerta.setContentText("Cant add the item.");
            alerta.showAndWait();
        }
    }

    public void loadItems(){
        ItemsServices servItems = new ItemsServices();
        itemList.getItems().clear();
        itemList.getItems().addAll(servItems.getAllItems());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
