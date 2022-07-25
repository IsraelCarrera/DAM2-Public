package dao.hibernate;

import dao.DAOCustomers;
import dao.HibernateUtils;
import lombok.extern.log4j.Log4j2;
import model.Customer;
import model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


import java.util.List;

@Log4j2
public class HibernateDAOCustomers implements DAOCustomers {
    Session session;

    //HECHO
    @Override
    public Customer get(int id) {
        return null;
    }

    //HECHO
    @Override
    public List<Customer> getAll() {
        List<Customer> customers = null;
        try{
            session = HibernateUtils.getSession();
            customers = session.createNamedQuery("get_all_Customers",Customer.class).getResultList();
        }catch (Exception e){
            log.error(e.getMessage(),e);
        } finally {
            session.close();
        }
        return customers;
    }

    //HECHO
    @Override
    public int save(Customer t, User u) {
        Transaction transaction = null;
        int idCustomer= 0;
        try{
            session = HibernateUtils.getSession();
            transaction = session.beginTransaction();
            int idUser = (int) session.save(u);
            t.setIdCustomer(idUser);
            idCustomer= (int) session.save(t);
            transaction.commit();
        }catch (Exception e){
            if (transaction != null) {
                transaction.rollback();
            }
            log.error(e.getMessage(),e);
        }finally {
            session.close();
        }
        return idCustomer;
    }

    //HECHO
    @Override
    public void update(Customer t) {
        Transaction transaction = null;
        try{
            session = HibernateUtils.getSession();
            transaction = session.beginTransaction();
            session.update(t);
            transaction.commit();
        }catch (Exception e){
            if (transaction != null) {
                transaction.rollback();
            }
            log.error(e.getMessage(),e);
        }finally {
            session.close();
        }
    }

    //HECHO
    @Override
    public boolean delete(Customer t, User u) {
        boolean isDelete = false;
        Transaction transaction = null;
        try{
            session = HibernateUtils.getSession();
            transaction = session.beginTransaction();
            session.delete(t);
            session.delete(u);
            transaction.commit();
            isDelete=true;
        }catch (Exception e){
            if (transaction != null) {
                transaction.rollback();
            }
            log.error(e.getMessage(),e);
        }finally {
            session.close();
        }

        return isDelete;
    }
}
