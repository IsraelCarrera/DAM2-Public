package dao.hibernate;

import dao.DAOItems;
import dao.HibernateUtils;
import lombok.extern.log4j.Log4j2;
import model.Item;
import model.Purchase;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;


@Log4j2
public class HibernateDAOItems implements DAOItems {
    Session session;

    //Hecho
    @Override
    public Item get(int id) {
        Item item = null;
        try {
            session = HibernateUtils.getSession();
            item = session.get(Item.class, id);
        }catch (Exception e){
            log.error(e.getMessage(),e);
        } finally {
            session.close();
        }
        return item;
    }

    //Hecho
    @Override
    public List<Item> getAll() {
        List<Item> items = null;
        try {
            session = HibernateUtils.getSession();
            items = session.createNamedQuery("get_all_Items",Item.class).getResultList();
        }catch (Exception e){
            log.error(e.getMessage(),e);
        } finally {
            session.close();
        }
        return items;
    }


    //Hecho
    @Override
    public int save(Item t) {
        //Save 1 -> Done // Save 2 -> no realizado.
        int isSave=0;
        try {
            session = HibernateUtils.getSession();
            isSave = (int) session.save(t);
        }catch (Exception e){
            log.error(e.getMessage(),e);
        } finally {
        session.close();
        }
        return isSave;
    }

    //Hecho. Hay que usar el commit
    @Override
    public void update(Item t) {
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
    public boolean delete(Item t, List<Purchase> purchases) {
        boolean isDelete;
        if (purchases == null) {
            isDelete = deleteWithoutPurchases(t);
        } else {
            isDelete = deleteWithPurchases(t, purchases);
        }
        return isDelete;
    }


    private boolean deleteWithPurchases(Item t, List<Purchase> purchases) {
        boolean isDelete=false;
        Transaction transaction = null;
        try{
            session = HibernateUtils.getSession();
            transaction = session.beginTransaction();
            for(Purchase p : purchases){
                session.delete(p);
            }
            session.delete(t);
            transaction.commit();
            isDelete=true;

        }catch (Exception e){
            if(transaction!= null){
                transaction.rollback();
            }
            log.error(e.getMessage(),e);
        }finally {
            session.close();
        }
        return isDelete;
    }

    private boolean deleteWithoutPurchases(Item t) {
        boolean isDelete=false;
        try{
            session = HibernateUtils.getSession();
            session.delete(t);
            isDelete = true;
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }finally {
            session.close();
        }
        return isDelete;

    }

    @Override
    public boolean existId(int id) {
        boolean exist = false;
        List<Item> items = getAll();
        for (Item i : items) {
            if (i.getIdItem() == id) {
                exist = true;
            }
        }
        return exist;
    }

}
