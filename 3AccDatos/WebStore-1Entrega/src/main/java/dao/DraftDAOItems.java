package dao;

import model.Item;

import java.util.ArrayList;
import java.util.List;

public class DraftDAOItems{
    public static List<Item> daoItems;

    public DraftDAOItems(){
        daoItems = new ArrayList<>();
        Item i1 = new Item(1,"mouse","acme",10.5);
        Item i2 = new Item(2,"speaker","Speak",50.9);
        Item i3 = new Item(3,"PC","acme",1540.6);
        daoItems.add(i1);
        daoItems.add(i2);
        daoItems.add(i3);
    }

    public Item get(int id) {
        return daoItems.get(id);
    }

    public List<Item> getAll() {
        return daoItems;
    }

    public void save(Item t) {
        daoItems.add(t);
    }


    public void update(Item t) {

    }


    public void delete(Item t) {
        daoItems.remove(t);
    }
}