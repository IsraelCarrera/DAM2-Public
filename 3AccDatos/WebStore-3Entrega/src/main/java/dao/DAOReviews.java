/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.Review;

import java.util.List;

/**
 *
 */
public interface DAOReviews {

    Review get(int id);

    List<Review> getAll();

    int save(Review t);

    boolean update(Review t);

    boolean delete(Review t);

    List<Review> getReviewsByItemId(int idItem);

    List<Review> getReviewsByIdPurchase(int idPurchase);

    List<Review> getReviewsByCustomerId(int idCustomer);

    List<Review> getReviewsByItemAndCustomer(int idItem, int idCustomer);

    int countById(int idItem);

}
