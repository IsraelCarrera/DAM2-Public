/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import lombok.Builder;

import java.util.ArrayList;
import java.util.Objects;

/**
 * @author dam2
 */


@Builder
public class Customer {

    private String dniCustomer;
    private String name;
    private String phone;
    private String address;

    public Customer() {
    }

    public Customer(String dniCustomer, String name, String phone, String address) {
        this.dniCustomer = dniCustomer;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public Customer(String name, String phone, String address) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public String getDniCustomer() {
        return dniCustomer;
    }

    public void setDniCustomer(String dniCustomer) {
        this.dniCustomer = dniCustomer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String toStringShort() {
        return dniCustomer + " - " + name;
    }

    public String toStringTexto() {
        return dniCustomer + "/" + name + "/" + phone + "/" + address + "/" ;
    }


    @Override
    public String toString() {
        return toStringShort();
    }

    //Equals basado en DNI


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(dniCustomer, customer.dniCustomer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dniCustomer);
    }
}
