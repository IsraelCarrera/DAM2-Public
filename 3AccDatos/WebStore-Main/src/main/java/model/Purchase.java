/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import lombok.Builder;

import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author Laura
 */
@Builder
public class Purchase {

    private String idPurchase;
    private String idCustomer;
    private String nameItem;
    private LocalDate date;
    public Purchase() {
    }
    //Utilizar la clase java.utils RandomUUID O también coger la última purchase del usuario y añadir su id.
    public Purchase(String idPurchase, String customer, String item, LocalDate date) {
        this.idPurchase = idPurchase;
        this.idCustomer = customer;
        this.nameItem = item;
        this.date = date;
    }
    public Purchase(String customer, String item, LocalDate date) {
        this.idCustomer = customer;
        this.nameItem = item;
        this.date = date;
    }

    public String getIdPurchase() {
        return idPurchase;
    }

    public void setIdPurchase(String idPurchase) {
        this.idPurchase = idPurchase;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return toStringShort();
    }
    
    public String toStringForClientInfo() {
        return "ID: " + idPurchase + "  ItemID: " + nameItem + "  Date: " + date + "\n";
    }
    public String toStringShort(){
        return "NameItem: " + nameItem;
    }

    public String toStringTexto() {
        return "CustomerDNI: " + idCustomer + "  ItemName: " + nameItem + "  Date: " + date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purchase purchase = (Purchase) o;
        return Objects.equals(idPurchase, purchase.idPurchase);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPurchase);
    }
}
