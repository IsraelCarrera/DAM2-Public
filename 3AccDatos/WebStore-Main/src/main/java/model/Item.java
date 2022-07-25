/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import lombok.Builder;
import org.bson.types.ObjectId;

import java.util.Objects;

/**
 * @author Laura
 */

@Builder
public class Item {

    private ObjectId idItem;
    private String name;
    private String company;
    private double price;

    public Item() {
    }

    public Item(ObjectId idItem, String name, String company, double price) {
        this.idItem = idItem;
        this.name = name;
        this.company = company;
        this.price = price;
    }

    public Item(String name, String company, double price) {
        this.name = name;
        this.company = company;
        this.price = price;
    }

    public ObjectId getIdItem() {
        return idItem;
    }

    public void setIdItem(ObjectId idItem) {
        this.idItem = idItem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return toStringShort();
    }

    public String toStringShort(){
        return " Name: " + name + "  Company: " + company;
    }

    public String toStringTextFile() {
        return idItem + ";" + name + ";" + company + ";" + price;
    }

    public String toStringVisual() {
        return "ID: " + idItem + "  Name: " + name + "  Company: " + company + " Price: " + String.format("%,.2f", price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return idItem == item.idItem && Double.compare(item.price, price) == 0 && Objects.equals(name, item.name) && Objects.equals(company, item.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idItem, name, company, price);
    }

}
