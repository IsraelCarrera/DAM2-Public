/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fx.controllers;

import fx.controllers.customers.*;
import fx.controllers.items.*;
import fx.controllers.purchases.*;
import fx.controllers.reviews.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author Laura
 */
public class FXMLPrincipalController implements Initializable {
    //Todos los menus para hacer que no sean seleccionables.
    //Los usernames y las contraseñas son las mismas.

    @FXML
    private MenuItem miAddCustomer;
    @FXML
    private MenuItem miListCustuomer;
    @FXML
    private MenuItem miFindCustomerId;
    @FXML
    private MenuItem miDeleteCustomer;
    @FXML
    private Menu menuPurchases;
    @FXML
    private Menu menuItems;
    @FXML
    private MenuItem miDeleteReview;

    //Reference to the top menu to change its visibility when needed.
    @FXML
    private BorderPane fxRoot;

    public void setFxRoot(BorderPane fxRoot) {
        this.fxRoot = fxRoot;
    }

    @FXML
    private MenuBar fxMenuTop;

    private int idUser;

    public int getIdUser() {
        return idUser;
    }

    // Get y set of the user to use it wherever we need it
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // References to other panes to load them and access their controllers
    private AnchorPane login;
    private FXMLLoginController loginController;
    private AnchorPane welcome;
    private FXMLWelcomeController welcomeController;

    private AnchorPane purchases;
    private FXMLAddPurchasesController purchasesController;
    private AnchorPane datePurchases;
    private FXMLDatePurchasesController datePurchasesController;
    private AnchorPane deletePurchases;
    private FXMLDeletePurchasesController deletePurchasesController;
    private AnchorPane updatePurchases;
    private FXMLUpdatePurchasesController updatePurchasesController;
    private AnchorPane listPurchases;
    private FXMLListPurchasesController listPurchasesController;


    private AnchorPane addCustomer;
    private FXMLAddCustomerController addCustomerController;
    private AnchorPane findCustomer;
    private FXMLfindCustomerController findCustomerController;
    private AnchorPane deleteCustomer;
    private FXMLdeleteCustomerController deleteCustomerController;
    private AnchorPane listCustomer;
    private FXMLListCustomerController listCustomerController;
    private AnchorPane updateCustomer;
    private FXMLUpdateCustomerController updateCustomerController;

    private AnchorPane addReview;
    private FXMLAddReviewController addReviewController;
    private AnchorPane findReview;
    private FXMLfindReviewController findReviewController;
    private AnchorPane deleteReview;
    private FXMLdeleteReviewController deleteReviewController;
    private AnchorPane listReview;
    private FXMLListReviewsController listReviewsController;
    private AnchorPane updateReview;
    private FXMLUpdateReviewController updateReviewController;

    private AnchorPane listItems;
    private FXMLListItemsController listItemsController;
    private AnchorPane addItem;
    private FXMLAddItemController addItemController;
    private AnchorPane deleteItem;
    private FXMLdeleteItemController deleteItemController;
    private AnchorPane findItem;
    private FXMLFindItemController findItemController;
    private AnchorPane updateItem;
    private FXMLUpdateItemController updateItemController;

    //Preload Pantallas iniciales
    public void preloadLogin() {
        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/FXMLLogin.fxml"));
            login = loaderMenu.load();
            loginController
                    = loaderMenu.getController();

            loginController.setPrincipal(this);
        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void preloadWelcome() {
        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/FXMLWelcome.fxml"));
            welcome = loaderMenu.load();
            welcomeController = loaderMenu.getController();

        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Preload Pantallas Purchases
    public void preloadPurchases() {
        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/purchases/FXMLAddPurchases.fxml"));
            purchases = loaderMenu.load();
            purchasesController = loaderMenu.getController();

        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void preloadDatePurchases() {
        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/purchases/FXMLDatePurchases.fxml"));
            datePurchases = loaderMenu.load();
            datePurchasesController = loaderMenu.getController();

        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void preloadDeletePurchases() {
        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/purchases/FXMLDeletePurchases.fxml"));
            deletePurchases = loaderMenu.load();
            deletePurchasesController = loaderMenu.getController();

        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void preloadUpdatePurchases() {
        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/purchases/FXMLUpdatePurchases.fxml"));
            updatePurchases = loaderMenu.load();
            updatePurchasesController = loaderMenu.getController();

        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void preloadListPurchases() {
        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/purchases/FXMLListPurchases.fxml"));
            listPurchases = loaderMenu.load();
            listPurchasesController = loaderMenu.getController();

        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Preload Pantallas Customer
    public void preloadAddCustomer() {
        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/customers/FXMLAddCustomer.fxml"));
            addCustomer = loaderMenu.load();
            addCustomerController = loaderMenu.getController();

        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void preloadFindCustomer() {
        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/customers/FXMLfindCustomer.fxml"));
            findCustomer = loaderMenu.load();
            findCustomerController = loaderMenu.getController();

        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void preloadListCustomer() {
        try {
            FXMLLoader loaderMenu = new FXMLLoader(getClass().getResource("/fxml/customers/FXMLListCustomer.fxml"));
            listCustomer = loaderMenu.load();
            listCustomerController = loaderMenu.getController();
        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void preloadDeleteCustomer() {
        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/customers/FXMLdeleteCustomer.fxml"));
            deleteCustomer = loaderMenu.load();
            deleteCustomerController = loaderMenu.getController();

        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void preloadUpdateCustomer() {
        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/customers/FXMLUpdateCustomer.fxml"));
            updateCustomer = loaderMenu.load();
            updateCustomerController = loaderMenu.getController();
        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Preload Pantallas Review
    public void preloadAddReview() {
        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/reviews/FXMLaddReview.fxml"));
            addReview = loaderMenu.load();
            addReviewController = loaderMenu.getController();

        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void preloadFindReview() {
        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/reviews/FXMLfindReview.fxml"));
            findReview = loaderMenu.load();
            findReviewController = loaderMenu.getController();

        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void preloadDeleteReview() {
        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/reviews/FXMLdeleteReview.fxml"));
            deleteReview = loaderMenu.load();
            deleteReviewController = loaderMenu.getController();

        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void preloadListReview() {
        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/reviews/FXMLListReview.fxml"));
            listReview = loaderMenu.load();
            listReviewsController = loaderMenu.getController();

        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void preloadUpdateReview() {
        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/reviews/FXMLUpdateReview.fxml"));
            updateReview = loaderMenu.load();
            updateReviewController = loaderMenu.getController();

        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Preload Pantallas Item
    public void preloadUpdateItem() {
        updateItemController = new FXMLUpdateItemController();
        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/items/FXMLUpdateItem.fxml"));
            updateItem = loaderMenu.load();
            updateItemController = loaderMenu.getController();

        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void preloadListItems() {
        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/items/FXMLListItems.fxml"));
            listItems = loaderMenu.load();
            listItemsController = loaderMenu.getController();
        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void preloadDeleteItems() {
        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/items/FXMLDeleteItem.fxml"));
            deleteItem = loaderMenu.load();
            deleteItemController = loaderMenu.getController();
        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void preloadFindItems() {
        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/items/FXMLFindItem.fxml"));
            findItem = loaderMenu.load();
            findItemController = loaderMenu.getController();
        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void preloadAddItem() {
        addItemController = new FXMLAddItemController();
        try {
            FXMLLoader loaderMenu = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/items/FXMLAddItem.fxml"));
            addItem = loaderMenu.load();
            addItemController = loaderMenu.getController();

        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //Cargas Login/welcome.
    public void chargeLogin() {
        fxRoot.setCenter(login);
        fxMenuTop.setVisible(false);
    }

    public void chargeWelcome() {
        welcomeController.setLogin(this.getUsername());
        fxMenuTop.setVisible(true);
        fxRoot.setCenter(welcome);
        idUser = loginController.getId();
        if (idUser != 0) {
            showUser();
        }
        if (idUser == 0) {
            showAdmin();
        }
    }

    //Cambios Purchase
    public void chargePurchases() {

        purchasesController.load();
        fxRoot.setCenter(purchases);
    }

    public void chargeDatePurchases() {
        datePurchasesController.loadPurchasesList();
        fxRoot.setCenter(datePurchases);
    }

    public void chargeDeletePurchases() {
        deletePurchasesController.loadPurchases();
        fxRoot.setCenter(deletePurchases);
    }

    public void chargeUpdatePurchase() {
        updatePurchasesController.load();
        fxRoot.setCenter(updatePurchases);
    }

    public void chargeListPurchase() {
        listPurchasesController.loadPurchaseList();
        fxRoot.setCenter(listPurchases);
    }


    //Cambios Customer
    public void chargeUpdateCustomer() {
        updateCustomerController.getId(idUser);
        updateCustomerController.loadCustomersList();
        fxRoot.setCenter(updateCustomer);
    }

    public void chargeAddCustomer() {
        addCustomerController.loadCustomersList();
        fxRoot.setCenter(addCustomer);
    }

    public void chargeFindCustomer() {
        fxRoot.setCenter(findCustomer);
    }

    public void chargeDeleteCustomer() {
        deleteCustomerController.loadCustomersList();
        fxRoot.setCenter(deleteCustomer);
    }

    public void chargeListCustomer() {
        listCustomerController.loadCustomer();
        fxRoot.setCenter(listCustomer);
    }

    //Cambios Review
    public void chargeAddReview() {
        addReviewController.getId(idUser);
        addReviewController.loadCustomer();
        fxRoot.setCenter(addReview);
    }

    public void chargeDeleteReview() {
        deleteReviewController.loadCustomersList();
        fxRoot.setCenter(deleteReview);
    }

    public void chargeFindReview() {
        findReviewController.loadItems();
        findReviewController.getId(idUser);
        fxRoot.setCenter(findReview);
    }

    @FXML
    private void chargeUpdateReview() {
        updateReviewController.getId(idUser);
        updateReviewController.loadUpdateReviews();
        fxRoot.setCenter(updateReview);
    }

    @FXML
    private void chargeListReview() {
        listReviewsController.getId(idUser);
        listReviewsController.loadReviews();
        fxRoot.setCenter(listReview);
    }

    //Cambios Item
    public void chargeAddItem() {
        addItemController.loadItems();
        fxRoot.setCenter(addItem);
    }

    public void chargeUpdateItem() {
        updateItemController.loadItems();
        fxRoot.setCenter(updateItem);
    }

    public void listItems() {
        listItemsController.loadItemsList();
        fxRoot.setCenter(listItems);
    }

    public void deleteItem() {
        deleteItemController.loadItemDelete();
        fxRoot.setCenter(deleteItem);
    }

    public void chargeFindItem() {
        fxRoot.setCenter(findItem);
    }


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Funciones.
        //Preloads inicialez
        preloadWelcome();
        preloadLogin();

        //PreloadsPurchases
        preloadPurchases();
        preloadDatePurchases();
        preloadDeletePurchases();
        preloadUpdatePurchases();
        preloadListPurchases();

        //PreloadsCustomers
        preloadAddCustomer();
        preloadFindCustomer();
        preloadDeleteCustomer();
        preloadListCustomer();
        preloadUpdateCustomer();

        //PreloadsReviews
        preloadAddReview();
        preloadDeleteReview();
        preloadFindReview();
        preloadListReview();
        preloadUpdateReview();

        //PreloadsItems
        preloadListItems();
        preloadAddItem();
        preloadDeleteItems();
        preloadFindItems();
        preloadUpdateItem();

        chargeLogin();

    }

    private void showUser() {
        menuItems.setVisible(false);
        miAddCustomer.setVisible(false);
        miListCustuomer.setVisible(false);
        miFindCustomerId.setVisible(false);
        miDeleteCustomer.setVisible(false);
        menuPurchases.setVisible(false);
        miDeleteReview.setVisible(false);
    }


    private void showAdmin() {
        menuItems.setVisible(true);
        miAddCustomer.setVisible(true);
        miListCustuomer.setVisible(true);
        miFindCustomerId.setVisible(true);
        miDeleteCustomer.setVisible(true);
        menuPurchases.setVisible(true);
        miDeleteReview.setVisible(true);
    }
}
