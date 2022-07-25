/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.Purchase;

import java.util.List;

/**
 *
 */
public interface DAOPurchases {

    Purchase get(int id);
     
    List<Purchase> getAll();
     
    boolean save(Purchase t);
     
    void update(Purchase t);
     
    boolean delete(Purchase t);
}
