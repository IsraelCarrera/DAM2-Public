package dao;

import model.Customer;

import java.util.ArrayList;
import java.util.List;

public class DraftDAOCustomers implements DAOCustomers{
    private static List<Customer> dao;

    public DraftDAOCustomers(){
        dao= new ArrayList<>();
        /*Customer c1 = new Customer(1,"Pepe","4545454","Calle Perez 2");
        Customer c2 = new Customer(2, "Pepa", "2332123213", "Calle García Noblejas 4");
        Customer c3 = new Customer(3,"Patricia", "112312313123", "Calle probando 123");
        dao.add(c1);
        dao.add(c2);
        dao.add(c3);*/
    }

    @Override
    public Customer get(int id) {
        return null;
    }

    @Override
    public List<Customer> getAll() {
        return dao;
    }

    @Override
    public boolean save(Customer t) {
        return false;
    }

    @Override
    public boolean update(Customer t) {
        return false;
    }

    @Override
    public boolean delete(Customer t) {
        return false;
    }
}
