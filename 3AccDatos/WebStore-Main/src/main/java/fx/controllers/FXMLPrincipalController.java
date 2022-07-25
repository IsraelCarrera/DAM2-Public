/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fx.controllers;

import fx.controllers.customers.FXMLAddCustomerController;
import fx.controllers.customers.FXMLListCustomerController;
import fx.controllers.customers.FXMLdeleteCustomerController;
import fx.controllers.items.FXMLAddItemController;
import fx.controllers.items.FXMLListItemsController;
import fx.controllers.items.FXMLdeleteItemController;
import fx.controllers.purchases.FXMLAddPurchasesController;
import fx.controllers.purchases.FXMLDeletePurchasesController;
import fx.controllers.reviews.FXMLAddReviewController;
import fx.controllers.reviews.FXMLdeleteReviewController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import model.User;

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
    private MenuItem miDeleteCustomer;
    @FXML
    private Menu menuPurchases;
    @FXML
    private Menu menuItems;
    @FXML
    private MenuItem miDeleteReview;

    //Al igual, comento el código de creación de estas pantallas.

    //Reference to the top menu to change its visibility when needed.
    @FXML
    private BorderPane fxRoot;
    @FXML
    private MenuBar fxMenuTop;
    // Get y set of the user to use it wherever we need it
    // References to other panes to load them and access their controllers
    private AnchorPane login;
    private FXMLLoginController loginController;
    private AnchorPane welcome;
    private FXMLWelcomeController welcomeController;
    private AnchorPane purchases;
    private FXMLAddPurchasesController purchasesController;
    private AnchorPane deletePurchases;
    private FXMLDeletePurchasesController deletePurchasesController;
    private AnchorPane addCustomer;
    private FXMLAddCustomerController addCustomerController;
    private AnchorPane deleteCustomer;
    private FXMLdeleteCustomerController deleteCustomerController;
    private AnchorPane listCustomer;
    private FXMLListCustomerController listCustomerController;
    private AnchorPane addReview;
    private FXMLAddReviewController addReviewController;
    private AnchorPane deleteReview;
    private FXMLdeleteReviewController deleteReviewController;
    private AnchorPane listItems;
    private FXMLListItemsController listItemsController;
    private AnchorPane addItem;
    private FXMLAddItemController addItemController;
    private AnchorPane deleteItem;
    private FXMLdeleteItemController deleteItemController;

    private User user = null;
    public String getUsername() {
        return user.getUsername();
    }

    public void setUsername(User user) {
        this.user = user;
    }

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
        if (user.getTipo().equals("user")) {
            showUser();
        } else if (user.getTipo().equals("admin")) {
            showAdmin();
        }
    }

    //Cambios Purchase
    public void chargePurchases() {

        purchasesController.load();
        fxRoot.setCenter(purchases);
    }


    public void chargeDeletePurchases() {
        deletePurchasesController.loadPurchases();
        fxRoot.setCenter(deletePurchases);
    }


//    Cambios Customer

    public void chargeAddCustomer() {
        addCustomerController.loadCustomersList();
        fxRoot.setCenter(addCustomer);
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
        addReviewController.getUser(user);
        addReviewController.loadCustomer();
        fxRoot.setCenter(addReview);
    }

    public void chargeDeleteReview() {
        deleteReviewController.loadCustomersList();
        fxRoot.setCenter(deleteReview);
    }


    //Cambios Item
    public void chargeAddItem() {
        addItemController.loadItems();
        fxRoot.setCenter(addItem);
    }

    public void listItems() {
        listItemsController.loadItemsList();
        fxRoot.setCenter(listItems);
    }

    public void deleteItem() {
        deleteItemController.loadItemDelete();
        fxRoot.setCenter(deleteItem);
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
        preloadDeletePurchases();

        //PreloadsCustomers
        preloadAddCustomer();
        preloadDeleteCustomer();
        preloadListCustomer();


        //PreloadsReviews
        preloadAddReview();
        preloadDeleteReview();


        //PreloadsItems
        preloadListItems();
        preloadAddItem();
        preloadDeleteItems();

        chargeLogin();


    }

    private void showUser() {
        menuItems.setVisible(false);
        miAddCustomer.setVisible(false);
        miListCustuomer.setVisible(false);
        miDeleteCustomer.setVisible(false);
        menuPurchases.setVisible(false);
        miDeleteReview.setVisible(false);
    }


    private void showAdmin() {
        menuItems.setVisible(true);
        miAddCustomer.setVisible(true);
        miListCustuomer.setVisible(true);
        miDeleteCustomer.setVisible(true);
        menuPurchases.setVisible(true);
        miDeleteReview.setVisible(true);
    }

}
