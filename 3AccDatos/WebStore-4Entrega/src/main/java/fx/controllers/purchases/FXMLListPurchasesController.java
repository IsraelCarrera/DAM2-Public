package fx.controllers.purchases;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Customer;
import model.Item;
import model.Purchase;
import services.CustomersServices;
import services.ItemsServices;
import services.PurchasesServices;
import utils.Constantes;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class FXMLListPurchasesController implements Initializable {

    private final PurchasesServices ps = new PurchasesServices();
    private final ItemsServices is = new ItemsServices();
    private final CustomersServices cs = new CustomersServices();


    @FXML
    private Label lbInitialDate;
    @FXML
    private Label lbFinalDate;
    @FXML
    private DatePicker dpFinalDate;
    @FXML
    private DatePicker dpInitialDate;
    @FXML
    private ComboBox<String> cbListBy;
    @FXML
    private Label lbDate;
    @FXML
    private Label lbItem;
    @FXML
    private ComboBox<Item> cbItem;
    @FXML
    private Label lbCustomer;
    @FXML
    private ComboBox<Customer> cbCustomer;

    @FXML
    private ListView<Purchase> purchasesList;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbListBy.getItems().addAll(Constantes.ALL, Constantes.ITEMS, Constantes.CUSTOMERS, Constantes.DATE);
        invisibility();
        cbCustomer.getItems().addAll(cs.getAllCustomers());
        cbItem.getItems().addAll(is.getAllItems());
        cbListBy.setValue(Constantes.ALL);
    }

    @FXML
    private void btnSearch() {
        switch (cbListBy.getSelectionModel().getSelectedItem()) {
            case Constantes.ITEMS:
                searchItems();
                break;
            case Constantes.CUSTOMERS:
                searchCustomer();
                break;
            case Constantes.DATE:
                searchDate();
                break;
            default:
                searchAll();
                break;
        }
    }

    @FXML
    private void selectList() {
        switch (cbListBy.getSelectionModel().getSelectedItem()) {
            case Constantes.ITEMS:
                visibilityItems();
                break;
            case Constantes.CUSTOMERS:
                visibilityCustomer();
                break;
            case Constantes.DATE:
                visibilityDate();
                break;
            default:
                invisibility();
                break;
        }
    }

    //Visibilitys
    private void visibilityItems(){
        lbItem.setVisible(true);
        cbItem.setVisible(true);
        //Visible false all
        lbDate.setVisible(false);
        lbFinalDate.setVisible(false);
        lbInitialDate.setVisible(false);
        dpFinalDate.setVisible(false);
        dpInitialDate.setVisible(false);
    }

    private void visibilityCustomer(){
        lbCustomer.setVisible(true);
        cbCustomer.setVisible(true);
        //Visible false all
        lbDate.setVisible(false);
        lbFinalDate.setVisible(false);
        lbInitialDate.setVisible(false);
        dpInitialDate.setVisible(false);
        dpFinalDate.setVisible(false);
        lbItem.setVisible(false);
        cbItem.setVisible(false);
    }

    private void visibilityDate(){
        lbDate.setVisible(true);
        lbFinalDate.setVisible(true);
        lbInitialDate.setVisible(true);
        dpInitialDate.setVisible(true);
        dpFinalDate.setVisible(true);
        //Visible false all
        lbItem.setVisible(false);
        cbItem.setVisible(false);
        lbCustomer.setVisible(false);
        cbCustomer.setVisible(false);
    }

    private void invisibility(){
        //Date
        lbDate.setVisible(false);
        lbFinalDate.setVisible(false);
        lbInitialDate.setVisible(false);
        dpInitialDate.setVisible(false);
        dpFinalDate.setVisible(false);
        //Item
        lbItem.setVisible(false);
        cbItem.setVisible(false);
        //Customer
        lbCustomer.setVisible(false);
        cbCustomer.setVisible(false);
    }

    private void searchAll(){
        purchasesList.getItems().clear();
        List<Purchase> list = ps.getAllPurchases();
        if(list != null) {
            purchasesList.getItems().addAll(list);
        }else{
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText(Constantes.NO_DATA);
            alerta.showAndWait();
        }
    }

    private void searchItems(){
        Item item = cbItem.getSelectionModel().getSelectedItem();
        if(item!= null){
            purchasesList.getItems().clear();
            List<Purchase> list = ps.getPurchaseByIdItem(item.getIdItem());
            if(!list.isEmpty()){
                purchasesList.getItems().addAll(list);
            }else{
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setContentText(Constantes.NO_DATA);
                alerta.showAndWait();
            }
        }else{
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText(Constantes.YOU_HAVE_NOT_SELECTED_ANYTHING);
            alerta.showAndWait();
        }
    }

    private void searchCustomer(){
        Customer customer = cbCustomer.getSelectionModel().getSelectedItem();
        if(customer!= null){
            purchasesList.getItems().clear();
            List<Purchase> list = ps.getPurchasesByClientId(customer.getIdCustomer());
            if(!list.isEmpty()){
                purchasesList.getItems().addAll(list);
            }else{
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setContentText(Constantes.NO_DATA);
                alerta.showAndWait();
            }
        }else{
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText(Constantes.YOU_HAVE_NOT_SELECTED_ANYTHING);
            alerta.showAndWait();
        }
    }

    private void searchDate(){
        LocalDate iniDate = dpInitialDate.getValue();
        LocalDate finDate = dpFinalDate.getValue();
        purchasesList.getItems().clear();
        List<Purchase> list = ps.getPurchasesBetweenDates(iniDate,finDate);
        if(!list.isEmpty()){
            purchasesList.getItems().addAll(list);
        }else{
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText(Constantes.NO_DATA);
            alerta.showAndWait();
        }
    }
}
