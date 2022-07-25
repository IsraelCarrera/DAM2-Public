/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.Item;

import java.util.List;

/**
 *
 */
public interface DAOItems {

    Item get(int id);
     
    List<Item> getAll();
     
    void save(Item t);
     
    void update(Item t);
     
    boolean delete(Item t);

    boolean delete(int id);
}
