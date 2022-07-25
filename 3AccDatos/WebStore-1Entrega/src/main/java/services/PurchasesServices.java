/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dao.DAOPurchases;
import dao.FileDAOPurchases;
import model.Purchase;

/**
 *
 * @author dam2
 */
public class PurchasesServices {

    public List<Purchase> getAllPurchases() {
        DAOPurchases dao = new FileDAOPurchases();
        return dao.getAll();
    }

    public List<Purchase> searchByDate(LocalDate date) {
        FileDAOPurchases dao = new FileDAOPurchases();
        return dao.getPurchasesByDate(date);
    }

    public List<Purchase> getPurchasesByClientId(int id) {
        List<Purchase> purch =  null;
        return purch;
    }

    public boolean deletePurchase(Purchase purchase) {
        DAOPurchases dao = new FileDAOPurchases();
        return dao.delete(purchase);
     }

    public boolean addPurchase(int customerId, int itemId, LocalDate date) {
        Purchase newPurchase = new Purchase(customerId,itemId,date);
        DAOPurchases dp = new FileDAOPurchases();
        return dp.save(newPurchase);
    }

    public List<Purchase> checkPurchaseByIdItem(int idItem){
        FileDAOPurchases dp = new FileDAOPurchases();
        return dp.checkPurchaseByIdItem(idItem);
    }

    public void deleteManyPurchases(List<Purchase> purchases){
        for (Purchase p: purchases) {
            deletePurchase(p);
        }
    }
}
