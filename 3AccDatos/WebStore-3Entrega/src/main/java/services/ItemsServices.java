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
        return dao.getDAOItems().getAll();
    }

    public boolean itsId(int id) {
        return (dao.getDAOItems().existId(id));
    }

    public int addItem(Item item) {
       return dao.getDAOItems().save(item);
    }

    public boolean removeItem(Item i, List<Purchase> purchaseList) {
        return dao.getDAOItems().delete(i, purchaseList);
    }

    public Item getItem(int id) {
        return dao.getDAOItems().get(id);
    }

    public void updateItem(Item i) {
        dao.getDAOItems().update(i);
    }

    //Hago un daoItems en la interfaz para cerrar la conexión de la DBPool. Podría meterlo en na clase nueva, pero hacer 3 clases para una sola acción no lo veo muy eficaz.

    public void closeDbPool(){
        dao.getDAOItems().closeDBPool();
    }
}
