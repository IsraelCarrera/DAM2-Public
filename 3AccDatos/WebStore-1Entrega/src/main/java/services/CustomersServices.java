/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.ArrayList;

import dao.DAOCustomers;
import dao.DraftDAOCustomers;
import dao.FileDAOCustomers;
import model.Customer;

/**
 *
 * @author Laura
 */
public class CustomersServices {

    public ArrayList<Customer> getAllCustomers() {
        DAOCustomers dao = new FileDAOCustomers();
        return (ArrayList<Customer>) dao.getAll();
    }

    public Customer searchById(int id) {
        DAOCustomers dao = new FileDAOCustomers();
        return dao.get(id);
    }

    public boolean deleteCustomer(Customer customer) {
        DAOCustomers dao = new FileDAOCustomers();
        return dao.delete(customer);
    }

    public boolean addCustomer( String name, String phone, String address) {
        DAOCustomers dao = new FileDAOCustomers();
        Customer cust = new Customer(name,phone,address);
        return dao.save(cust);
    }

}
