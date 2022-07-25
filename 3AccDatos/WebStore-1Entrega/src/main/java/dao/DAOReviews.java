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
     
    void save(Review t);
     
    void update(Review t);
     
    void delete(Review t);
}
