/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.FactoryDAO;
import model.Customer;
import model.User;

import java.util.List;

/**
 * @author Laura
 */
public class CustomersServices {
    private final FactoryDAO dao;

    public CustomersServices() {
        dao = new FactoryDAO();
    }

    public List<Customer> getAllCustomers() {
        return dao.getDAOCustomers().getAll();
    }

    public Customer searchById(int id) {
        return dao.getDAOCustomers().get(id);
    }

    public boolean deleteCustomer(Customer customer, User user) {
        return dao.getDAOCustomers().delete(customer, user);
    }

    public int addCustomer(Customer c, User u) {
        return dao.getDAOCustomers().save(c, u);
    }

    public void updateCustomer(Customer c) {
        dao.getDAOCustomers().update(c);
    }

    public Customer getCustomerById(int id){
        return dao.getDAOCustomers().get(id);
    }
}
