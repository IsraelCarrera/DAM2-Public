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
    private int idCustomer;
    private int IdItem;
    private LocalDate date;
    public Purchase() {
    }

    public Purchase(int idCustomer, int item, LocalDate date) {
        this.idCustomer = idCustomer;
        this.IdItem = item;
        this.date = date;
    }

    public Purchase(String cadena){
        String [] s=cadena.split(";");
        this.idPurchase = Integer.parseInt(s[0]);
        this.idCustomer = Integer.parseInt(s[1]);
        this.IdItem = Integer.parseInt(s[2]);
        this.date = LocalDate.parse(s[3]);
    }
    public int getIdPurchase() {
        return idPurchase;
    }

    public void setIdPurchase(int idPurchase) {
        this.idPurchase = idPurchase;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public int getIdItem() {
        return IdItem;
    }

    public void setIdItem(int idItem) {
        this.IdItem = idItem;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ID: " + idPurchase + "  Customer: " + idCustomer + "  Item: " + IdItem + "  Date: " + date;
    }
    
    public String toStringForClientInfo() {
        return "ID: " + idPurchase + "  Item: " + IdItem + "  Date: " + date + "\n";
    }

    public String toStringTexto() {
        return idPurchase + ";" + idCustomer + ";" + IdItem + ";" + date;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purchase purchase = (Purchase) o;
        return idCustomer == purchase.idCustomer && IdItem == purchase.IdItem && Objects.equals(date, purchase.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCustomer, IdItem, date);
    }
}
