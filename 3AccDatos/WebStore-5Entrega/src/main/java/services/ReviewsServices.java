/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.FactoryDAO;
import model.Customer;
import model.Item;
import model.Purchase;
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
        return dao.getDAOItems().getAllReviews();
    }

    public boolean addReview(Review review, Purchase p) {
        boolean isAdd = false;
        if(dao.getDAOCustomers().saveReview(review,p)){
            isAdd = dao.getDAOItems().saveReview(review);
        }
        return isAdd;
    }

    public boolean deleteReview(Review r){
        boolean isDelete = false;
        if(dao.getDAOCustomers().deleteReview(r)){
            isDelete = dao.getDAOItems().deleteReview(r);
        }
        return isDelete;
    }

    public List<Review> reviewsByIdCustomer(String idCustomer){
        return dao.getDAOItems().getReviewsByIdCustomer(idCustomer);
    }

    public Review reviewByPurchase(Purchase p){
        return dao.getDAOItems().getReviewByPurchase(p);
    }


    public List<Review> searchByItem(Item t) {
        return dao.getDAOItems().getReviewsByItem(t);
    }
}
