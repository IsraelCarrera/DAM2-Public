/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.FactoryDAO;
import model.Customer;
import model.Item;
import model.Purchase;

import java.time.LocalDate;
import java.util.List;

/**
 * @author dam2
 */
public class PurchasesServices {
    private final FactoryDAO dao;

    public PurchasesServices() {
        dao = new FactoryDAO();
    }

    public List<Purchase> getAllPurchases() {
        return dao.getDAOCustomers().getAllPurchases();
    }

    public boolean addPurchase(Purchase p) {
        boolean isAdd = false;
        if(dao.getDAOCustomers().savePurchase(p)){
            isAdd= dao.getDAOItems().savePurchase(p);
        }
        return isAdd;
    }

    public boolean deletePurchase(Purchase p) {
        boolean isDelete = false;
        if(dao.getDAOCustomers().deletePurchase(p)){
            isDelete= dao.getDAOItems().deletePurchase(p);
        }
        return isDelete;
    }

    public  boolean checkPurchasesHaveReviews(Purchase p){
        return dao.getDAOItems().checkIfPurchaseHaveReview(p);
    }

    public List<Purchase> getPurchasesByClientId(String dni) {
        return dao.getDAOCustomers().getPurchasesByCustomer(dni);
    }

    public List<Purchase> getPurchasesByItem(Item i){
        return dao.getDAOItems().checkIfItemHavePurchases(i);
    }

    public void deletePurchasesOnlyInCustomer(List<Purchase> purchases){
        for (Purchase p :purchases) {
            dao.getDAOCustomers().deletePurchase(p);
        }
    }

}
