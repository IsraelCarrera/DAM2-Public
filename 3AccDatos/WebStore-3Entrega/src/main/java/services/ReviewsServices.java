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


    public List<Review> searchByItem(int id) {
        return dao.getDAOReviews().getReviewsByItemId(id);
    }


    public int addReview(Review review) {
        return dao.getDAOReviews().save(review);
    }

    public List<Review> reviewsByIdPurchase(int idPurchase) {
        return dao.getDAOReviews().getReviewsByIdPurchase(idPurchase);
    }

    public List<Review> reviewsByIdCustomer(int idCustomer){
        return dao.getDAOReviews().getReviewsByCustomerId(idCustomer);
    }

    public List <Review> reviewsByIdItemAndCustomer(int idItem, int idCustomer){
        return dao.getDAOReviews().getReviewsByItemAndCustomer(idItem,idCustomer);
    }

    public boolean updateReview(Review review) {
        return dao.getDAOReviews().update(review);
    }

    public boolean deleteReview(Review r){
        return dao.getDAOReviews().delete(r);
    }
}
