/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author Laura
 */
public class    Purchase {

    private int idPurchase;
    private Customer customer;
    private Item item;
    private LocalDate date;

    public Purchase() {
    }

    public Purchase(int idPurchase, Customer customer, Item item, LocalDate date) {
        this.idPurchase = idPurchase;
        this.customer = customer;
        this.item = item;
        this.date = date;
    }
    public Purchase(Customer customer, Item item, LocalDate date) {
        this.customer = customer;
        this.item = item;
        this.date = date;
    }

    public int getIdPurchase() {
        return idPurchase;
    }

    public void setIdPurchase(int idPurchase) {
        this.idPurchase = idPurchase;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item idItem) {
        this.item = idItem;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ID: " + idPurchase + "  CustomerID: " + customer.getIdCustomer() + "  ItemID: " + item.getIdItem() + "  Date: " + date;
    }
    
    public String toStringForClientInfo() {
        return "ID: " + idPurchase + "  ItemID: " + item.getIdItem() + "  Date: " + date + "\n";
    }

    public String toStringTexto() {
        return idPurchase + ";" + customer + ";" + item + ";" + date;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purchase purchase = (Purchase) o;
        return customer == purchase.customer && item == purchase.item && Objects.equals(date, purchase.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, item, date);
    }
}
