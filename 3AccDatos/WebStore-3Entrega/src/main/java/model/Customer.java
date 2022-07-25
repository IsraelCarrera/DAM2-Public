/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @author dam2
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Customer {

    private int idCustomer;
    private String name;
    private String telephone;
    private String address;
    private ArrayList<Review> reviews;

    public Customer() {
    }

    public Customer(int idCustomer, String name, String phone, String address) {
        this.idCustomer = idCustomer;
        this.name = name;
        this.address = address;
        this.telephone = phone;
        reviews = new ArrayList();
    }

    public Customer(String name, String phone, String address) {
        this.name = name;
        this.address = address;
        this.telephone = phone;
        reviews = new ArrayList();
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void addReview(Review review) {
        reviews.add(review);
    }

    public String toStringShort() {
        return idCustomer + " - " + name;
    }

    public String toStringTexto() {
        return idCustomer + "/" + name + "/" + telephone + "/" + address + "/" + reviews;
    }

    public String toStringReviews() {
        ArrayList<String> rev = new ArrayList();

        if (reviews != null) {
            for (int i = 0; i < reviews.size(); i++) {
                rev.add(reviews.get(i).toStringVisual());
            }
        }


        return "ID: " + idCustomer + "  Name: " + name
                + "\nPhone: " + telephone + "  Address: " + address
                + "\n\n======       Reviews done by this client:      ======\n\n" + rev;
    }

    @Override
    public String toString() {
        return "ID: " + idCustomer + "  Name: " + name + "  Phone: " + telephone + "  Address: " + address;
    }

    //Equals basado en name, phone and address


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(name, customer.name) && Objects.equals(telephone, customer.telephone) && Objects.equals(address, customer.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, telephone, address);
    }
}
