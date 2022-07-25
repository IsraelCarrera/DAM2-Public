package dao.hibernate;

import dao.DAOPurchases;
import dao.HibernateUtils;
import lombok.extern.log4j.Log4j2;
import model.Purchase;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Log4j2
public class HibernateDAOPurchases implements DAOPurchases {
    Session session;

    //HECHO
    @Override
    public Purchase get(int id) {
        Purchase purchase = null;
        try {
            session = HibernateUtils.getSession();
            purchase = session.get(Purchase.class, id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return purchase;
    }

    //HECHO
    @Override
    public List<Purchase> getAll() {
        List<Purchase> purchases = null;
        try {
            session = HibernateUtils.getSession();
            purchases = session
                    .createNamedQuery("get_all_Purchase", Purchase.class)
                    .getResultList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return purchases;
    }

    //HECHO
    @Override
    public int save(Purchase t) {
        int isSave = 0;
        try {
            session = HibernateUtils.getSession();
            isSave = (int) session.save(t);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return isSave;
    }

    //HECHO
    @Override
    public void update(Purchase t) {
        Transaction transaction = null;
        try {
            session = HibernateUtils.getSession();
            transaction = session.beginTransaction();
            session.update(t);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error(e.getMessage(), e);
        } finally {
            session.close();
        }
    }

    //HECHO
    @Override
    public boolean delete(Purchase t) {
        boolean isDelete = false;
        Transaction transaction = null;
        try {
            session = HibernateUtils.getSession();
            transaction = session.beginTransaction();
            session.delete(t);
            transaction.commit();
            isDelete = true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error(e.getMessage(), e);
        }
        return isDelete;
    }

    //HECHO
    @Override
    public List<Purchase> checkPurchaseByIdItem(int idItem) {
        List<Purchase> purchases = null;
        try {
            session = HibernateUtils.getSession();
            purchases = session
                    .createNamedQuery("get_purchase_by_idItem", Purchase.class)
                    .setParameter("idItem", idItem)
                    .getResultList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return purchases;
    }

    //HECHO
    @Override
    public List<Purchase> getPurchasesByDate(LocalDate date) {
        List<Purchase> purchases = null;
        try {
            session = HibernateUtils.getSession();
            purchases = session
                    .createNamedQuery("get_purchase_by_date", Purchase.class)
                    .setParameter("fecha", date)
                    .getResultList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return purchases;
    }

    //HECHO
    @Override
    public List<Purchase> getPurchasesByIdCostumer(int idCostumer) {
        List<Purchase> purchases = null;
        try {
            session = HibernateUtils.getSession();
            purchases = session
                    .createNamedQuery("get_purchase_by_idCustomer", Purchase.class)
                    .setParameter("idCostumer", idCostumer)
                    .getResultList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return purchases;
    }

    @Override
    public List<Purchase> getPurchasesBetweenDates(LocalDate iniDate, LocalDate finDate) {
        List<Purchase> purchases = null;
        try {
            session = HibernateUtils.getSession();
            purchases = session
                    .createNamedQuery("get_purchase_between_dates", Purchase.class)
                    .setParameter("iniDate", iniDate)
                    .setParameter("finDate", finDate)
                    .getResultList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return purchases;
    }

    @Override
    public double countPurchasesInMonth(int idItem) {
        Long countPurchases = null;
        LocalDate.now();
        try {
            session = HibernateUtils.getSession();
            countPurchases = session
                    .createNamedQuery("count_purchase_between_dates", Long.class)
                    .setParameter("idItem", idItem)
                    .setParameter("iniDate", LocalDate.now().minusMonths(1).with(TemporalAdjusters.firstDayOfMonth()))
                    .setParameter("finDate", LocalDate.now().minusMonths(1).with(TemporalAdjusters.lastDayOfMonth()))
                    .getResultList().get(0);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return countPurchases;
    }
}
