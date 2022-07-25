/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.io.File;
import java.util.ArrayList;


import dao.DAOItems;
import dao.FileDAOItems;
import model.Item;

/**
 *
 * @author dam2
 */
public class ItemsServices {
    
    public ArrayList<Item> getAllItems() {
        DAOItems dao = new FileDAOItems();
        return (ArrayList<Item>) dao.getAll();
    }

    public boolean itsId(int id){
        FileDAOItems dao = new FileDAOItems();
        return (dao.existId(id));
    }

    public boolean addItem(int id, String name, String company, double price){
        DAOItems dao = new FileDAOItems();
        if(name.isEmpty() || company.isEmpty()){
            return false;
        }else {
            dao.save(new Item(id, name, company, price));
            return true;
        }
    }

    public boolean removeItem(Object item){
        //Check purchases
        //Añadir un delete con ID y no con un objeto item y modificarlo. Mejor ponerlo en otra función en PurchasesServices (Ya creada, usarla) y solo modificar esta con el Int id.
        //Hay que modificar también con un alerta de confirmación. Si le da al botón borrar, sino, no.
        DAOItems dao = new FileDAOItems();
        Item i = (Item) item;
        return dao.delete(i);
    }

    public boolean removeItem(int id){
        DAOItems dao = new FileDAOItems();
        return dao.delete(id);
    }

    public Item getItem(int id){
        DAOItems dao = new FileDAOItems();
        return dao.get(id);
    }
}
