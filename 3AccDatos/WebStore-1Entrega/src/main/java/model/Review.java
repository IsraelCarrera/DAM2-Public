/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;

/**
 *
 * @author dam2
 */
public class Review {

    private int idReview;
    private int rating;
    private String title;
    private String description;
    private LocalDate date;
    private int customerId;
    private int itemId;
    private int purchaseId;

    public Review() {
    }

    public Review(int idReview, int rating, String title, String description, int customerId, int itemId, int purchaseId) {
        this.idReview = idReview;
        this.rating = rating;
        this.title = title;
        this.description = description;
        this.date = LocalDate.now();
        this.customerId = customerId;
        this.itemId = itemId;
        this.purchaseId = purchaseId;
    }

    public int getIdReview() {
        return idReview;
    }

    public void setIdReview(int idReview) {
        this.idReview = idReview;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getCustomer() {
        return customerId;
    }

    public void setCustomer(int customer) {
        this.customerId = customer;
    }

    public int getItem() {
        return itemId;
    }

    public void setItem(int item) {
        this.itemId = item;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
    }
    
    

    @Override
    public String toString() {
        return "No. " + idReview + "  Item: " + itemId + "\nRating: " + rating + "\nTitle: " + title + "\nComment: " + description + "\nDate: " + date + "  Customer: " + customerId + "  Purchase no. " + purchaseId;
    }

    public String toStringVisual() {
        return "No. " + idReview + "  Item: " + itemId + "  Rating: " + rating + "\nTitle: " + title + "\nComment: " + description + "\nDate: " + date + "\n____________________________________________________________\n";
    }

    public String toStringTexto() {
        return idReview + ":" + rating + ":" + title + ":" + description + ":" + date + ":" + customerId + ":" + itemId + ":" + purchaseId;
    }

}
