/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fx.controllers.items;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import model.Item;
import services.ItemsServices;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 */
public class FXMLListItemsController implements Initializable {

    private final ItemsServices servItems = new ItemsServices();

    @FXML
    private ListView<Item> itemsList;

    public void loadItemsList() {
        itemsList.getItems().clear();
        itemsList.getItems().addAll(servItems.getAllItems());
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadItemsList();
    }

}
