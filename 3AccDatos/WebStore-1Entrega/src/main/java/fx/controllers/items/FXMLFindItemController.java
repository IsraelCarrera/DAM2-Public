package fx.controllers.items;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Item;
import services.ItemsServices;

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
        try{
            int id = Integer.parseInt(idBox.getText());
            Item item = is.getItem(id);
            if (item != null) {
                itemList.getItems().clear();
                itemList.getItems().add(item);
            }else{
                alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setContentText("There aren't any item with this ID.");
                alerta.showAndWait();
            }
        }catch (Exception ex){
            alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText("You have to write a number.");
            alerta.showAndWait();
        }
    }
}
