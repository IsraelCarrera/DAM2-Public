/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.FactoryDAO;
import model.Customer;
import model.Review;

import java.util.List;

/**
 * @author Laura
 */
public class ReviewsServices {
    private final FactoryDAO dao;

    public ReviewsServices() {
        dao = new FactoryDAO();
    }


    public List<Review> getAllReviews() {
        return dao.getDAOReviews().getAll();
    }

    public void deleteReview(Customer customer, Review review) {
    }

    public List<Review> searchByItem(int id) {
        return dao.getDAOReviews().getReviewsByItemId(id);
    }

    public Review createReview() {
        Review rev = null;
        return rev;
    }

    public void addReview(Customer customer, Review review) {
    }

    public List<Review> reviewsByIdPurchase(int idPurchase) {
        return dao.getDAOReviews().getReviewsByIdPurchase(idPurchase);
    }
}
