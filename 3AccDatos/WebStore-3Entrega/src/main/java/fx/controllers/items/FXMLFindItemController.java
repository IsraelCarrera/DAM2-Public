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

public class FXMLFindItemController implements Initializable {

    @FXML
    private TextField idBox;
    @FXML
    private ListView<Item> itemList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void searchById() {
        ItemsServices is = new ItemsServices();
        Alert alerta;
        try {
            int id = Integer.parseInt(idBox.getText());
            Item item = is.getItem(id);
            if (item != null) {
                itemList.getItems().clear();
                itemList.getItems().add(item);
            } else {
                alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setContentText(Constantes.ITEM_NO_ID);
                alerta.showAndWait();
            }
        } catch (Exception ex) {
            alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText(Constantes.WRITE_NUMBER);
            alerta.showAndWait();
        }
    }
}
