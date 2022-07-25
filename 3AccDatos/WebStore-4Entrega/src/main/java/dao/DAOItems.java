/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.Item;
import model.Purchase;

import java.util.List;

/**
 *
 */
public interface DAOItems {

    Item get(int id);

    List<Item> getAll();

    int save(Item t);

    void update(Item t);

    boolean delete(Item t, List<Purchase> purchases);

    boolean existId(int id);

}
