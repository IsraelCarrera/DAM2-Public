/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.Customer;
import model.Item;
import model.Purchase;
import model.Review;
import org.bson.types.ObjectId;

import java.util.List;

/**
 *
 */
public interface DAOItems {

    List<Item> getAllItems();
    boolean saveItem(Item t);
    boolean deleteItem(Item t);
    boolean savePurchase(Purchase p);
    boolean deletePurchase(Purchase p);
    List<Review> getAllReviews();
    boolean saveReview(Review r);
    boolean deleteReview(Review r);
    List<Review> getReviewsByIdCustomer(String dni);
    boolean checkIfPurchaseHaveReview(Purchase p);
    boolean checkIfItemHaveReviews(Item t);
    List<Purchase> checkIfItemHavePurchases(Item t);
    Review getReviewByPurchase(Purchase p);
    int getNumberPurchases(Item t);
    double getAverageRating(Item t);
    List<Review> getReviewsByItem(Item t);
}
