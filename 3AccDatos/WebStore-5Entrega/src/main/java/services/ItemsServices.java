/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.FactoryDAO;
import model.Item;
import model.Purchase;

import java.util.List;

/**
 * @author dam2
 */
public class ItemsServices {
    private final FactoryDAO dao;
    public ItemsServices() {
        dao = new FactoryDAO();
    }
    public List<Item> getAllItems() {
        return dao.getDAOItems().getAllItems();
    }
    public boolean addItem(Item item) {
       return dao.getDAOItems().saveItem(item);
    }
    public boolean removeItem(Item i) {
        return dao.getDAOItems().deleteItem(i);
    }
    public boolean itemHaveReviews(Item i){
        return dao.getDAOItems().checkIfItemHaveReviews(i);
    }
    public int getPurchasesMonth(Item i){
        return dao.getDAOItems().getNumberPurchases(i);
    }
    public double averageRatingItem(Item t){
        return dao.getDAOItems().getAverageRating(t);
    }


}
