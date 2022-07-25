/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import lombok.Builder;

import java.time.LocalDate;

/**
 * @author dam2
 */
@Builder
public class Review {

    private String idPurchase;
    private String nameItem;
    private String idCustomer;
    private int rating;
    private String title;
    private String description;
    private LocalDate date;

    public Review() {
    }

    public Review( int rating, String title, String description, String purchase,String nameItem,String idCustomer) {
        this.rating = rating;
        this.title = title;
        this.description = description;
        this.date = LocalDate.now();
        this.idPurchase = purchase;
        this.nameItem = nameItem;
        this.idCustomer = idCustomer;
    }

    public Review(String idPurchase, String nameItem, String idCustomer, int rating, String title, String description, LocalDate date) {
        this.idPurchase = idPurchase;
        this.nameItem = nameItem;
        this.idCustomer = idCustomer;
        this.rating = rating;
        this.title = title;
        this.description = description;
        this.date = date;
    }

    public String getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(String idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getNameItem() {
        return nameItem;
    }

    public void setNameItem(String nameItem) {
        this.nameItem = nameItem;
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

    public String getIdPurchase() {
        return idPurchase;
    }

    public void setIdPurchase(String idPurchase) {
        this.idPurchase = idPurchase;
    }


    @Override
    public String toString() {
        return "  Purchase no. " + idPurchase + "\nRating: " + rating + "\nTitle: " + title + "\nComment: " + description + "\nDate: " + date ;
    }

//    public String toStringVisual() {
//        return "No. " + idReview + "  ItemID: " + idPurchase.getIdItem() + "  Rating: " + rating + "\nTitle: " + title + "\nComment: " + description + "\nDate: " + date + "\n____________________________________________________________\n";
//    }
//
//    public String toStringTexto() {
//        return idReview + ":" + rating + ":" + title + ":" + description + ":" + date + ":" + idPurchase.getIdCustomer() + ":" + idPurchase.getIdItem() + ":" + idPurchase.getIdPurchase();
//    }

}
