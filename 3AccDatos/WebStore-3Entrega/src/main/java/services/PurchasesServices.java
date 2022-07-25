/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.FactoryDAO;
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
        return dao.getDAOPurchases().getAll();
    }

    public List<Purchase> searchByDate(LocalDate date) {
        return dao.getDAOPurchases().getPurchasesByDate(date);
    }

    public List<Purchase> getPurchasesByClientId(int id) {
        return dao.getDAOPurchases().getPurchasesByIdCostumer(id);
    }

    public boolean deletePurchase(Purchase purchase) {
        return dao.getDAOPurchases().delete(purchase);
    }

    public int addPurchase(Purchase p) {
        return dao.getDAOPurchases().save(p);
    }

    public List<Purchase> checkPurchaseByIdItem(int idItem) {
        return dao.getDAOPurchases().checkPurchaseByIdItem(idItem);
    }

    public void deleteManyPurchases(List<Purchase> purchases) {
        for (Purchase p : purchases) {
            deletePurchase(p);
        }
    }

    public void updatePurchase(Purchase p) {
        dao.getDAOPurchases().update(p);
    }
}
