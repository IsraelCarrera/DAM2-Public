package fx.controllers.items;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Item;
import services.ItemsServices;
import utils.Constantes;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLUpdateItemController implements Initializable {
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
    private void updateItem() {
        Alert alerta;
        if (itemList.getSelectionModel().getSelectedItem() != null) {
            ItemsServices is = new ItemsServices();
            try {
                int id = Integer.parseInt(idBox.getText());
                String name = nameBox.getText();
                String company = companyBox.getText();
                double price = (double)Math.round(Double.parseDouble(priceBox.getText()) * 100d) / 100d;
                Item item = new Item(id, name, company, price);
                is.updateItem(item);
                itemList.getItems().set(itemList.getSelectionModel().getSelectedIndex(), item);
                clean();
                alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setContentText(Constantes.ITEM_UPDATE_FINE);
            } catch (Exception e) {
                alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setContentText(Constantes.ITEM_UPDATE_WRONG);
            }
        } else {
            alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setContentText(Constantes.ITEM_NO_SELECTED);
        }
        alerta.showAndWait();
    }

    public void loadItems() {
        ItemsServices servItems = new ItemsServices();
        itemList.getItems().clear();
        itemList.getItems().addAll(servItems.getAllItems());
    }


    @FXML
    private void showItem() {
        if (itemList.getSelectionModel().getSelectedItem() != null) {
            idBox.setText(String.valueOf(itemList.getSelectionModel().getSelectedItem().getIdItem()));
            nameBox.setText(itemList.getSelectionModel().getSelectedItem().getName());
            companyBox.setText(itemList.getSelectionModel().getSelectedItem().getCompany());
            priceBox.setText(String.valueOf(itemList.getSelectionModel().getSelectedItem().getPrice()));
        }
    }

    private void clean() {
        idBox.setText("");
        nameBox.setText("");
        companyBox.setText("");
        priceBox.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idBox.setEditable(false);
    }
}