/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.Customer;
import model.Purchase;
import model.Review;
import model.User;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.eq;

/**
 *
 */
public interface DAOCustomers {

    Customer getCustomer(String id);
    List<Customer> getAllCustomer();
    boolean saveCustomer(Customer t);
    boolean deleteCustomer(String dni);
    List<Purchase> getAllPurchases();
    boolean savePurchase(Purchase p);
    boolean deletePurchase(Purchase p);
    boolean saveReview(Review r, Purchase p);
    boolean deleteReview(Review r);
    List<Purchase> getPurchasesByCustomer(String dni);
    boolean checkIfCustomerHavePurchases(Customer t);
}
