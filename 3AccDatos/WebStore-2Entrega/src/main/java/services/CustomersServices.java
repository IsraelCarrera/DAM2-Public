/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.FactoryDAO;
import model.Customer;

import java.util.ArrayList;

/**
 * @author Laura
 */
public class CustomersServices {
    private final FactoryDAO dao;

    public CustomersServices() {
        dao = new FactoryDAO();
    }

    public ArrayList<Customer> getAllCustomers() {
        return (ArrayList<Customer>) dao.getDAOCustomers().getAll();
    }

    public Customer searchById(int id) {
        return dao.getDAOCustomers().get(id);
    }

    public boolean deleteCustomer(Customer customer) {
        return dao.getDAOCustomers().delete(customer);
    }

    public boolean addCustomer(Customer c) {
        return dao.getDAOCustomers().save(c);
    }

    public void updateCustomer(Customer c) {
        dao.getDAOCustomers().update(c);
    }

}
