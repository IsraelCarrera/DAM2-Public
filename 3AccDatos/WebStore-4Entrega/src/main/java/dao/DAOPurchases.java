/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.Purchase;

import java.time.LocalDate;
import java.util.List;

/**
 *
 */
public interface DAOPurchases {

    Purchase get(int id);

    List<Purchase> getAll();

    int save(Purchase t);

    void update(Purchase t);

    boolean delete(Purchase t);

    List<Purchase> checkPurchaseByIdItem(int idItem);

    List<Purchase> getPurchasesByDate(LocalDate date);

    List<Purchase> getPurchasesByIdCostumer(int idCostumer);

    List<Purchase> getPurchasesBetweenDates(LocalDate iniDate, LocalDate finDate);

    double countPurchasesInMonth(int idItem);
}
