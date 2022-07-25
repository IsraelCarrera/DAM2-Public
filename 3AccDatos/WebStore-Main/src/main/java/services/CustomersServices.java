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
        return dao.getDAOCustomers().getAllCustomer();
    }

    public Customer searchById(String id) {
        return dao.getDAOCustomers().getCustomer(id);
    }

    public boolean deleteCustomer(String dni) {
        boolean isDelete = false;
        if(dao.getDAOUsers().deleteUser(dni)){
            isDelete=dao.getDAOCustomers().deleteCustomer(dni);
        }
        return isDelete;
    }

    public boolean addCustomer(Customer c, User u) {
        boolean isAdd = false;
        if (dao.getDAOUsers().addUser(u)){
            isAdd=dao.getDAOCustomers().saveCustomer(c);
        }
        return isAdd;
    }

    public boolean customerHavePurchases(Customer c){
        return dao.getDAOCustomers().checkIfCustomerHavePurchases(c);
    }
}
