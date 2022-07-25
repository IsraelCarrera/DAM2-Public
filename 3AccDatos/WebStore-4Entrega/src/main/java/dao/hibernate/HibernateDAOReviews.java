package dao.hibernate;

import dao.DAOReviews;
import dao.HibernateUtils;
import lombok.extern.log4j.Log4j2;
import model.Review;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

@Log4j2
public class HibernateDAOReviews implements DAOReviews {
    Session session;

    //HECHO
    @Override
    public Review get(int id) {
        Review review = null;
        try {
            session = HibernateUtils.getSession();
            review = session.get(Review.class, id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return review;
    }

    //HECHO
    @Override
    public List<Review> getAll() {
        List<Review> reviews = null;
        try {
            session = HibernateUtils.getSession();
            reviews = session
                    .createNamedQuery("get_all_Reviews",Review.class)
                    .getResultList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return reviews;
    }

    //HECHO
    @Override
    public int save(Review t) {
        //Save 1 -> Done // Save 2 -> no realizado.
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
    public boolean update(Review t) {
        boolean isUpdate = false;
        Transaction transaction = null;
        try {
            session = HibernateUtils.getSession();
            transaction = session.beginTransaction();
            session.update(t);
            transaction.commit();
            isUpdate = true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return isUpdate;
    }

    //HECHO
    @Override
    public boolean delete(Review t) {
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
    public List<Review> getReviewsByItemId(int idItem) {
        List<Review> reviews = null;
        try {
            session = HibernateUtils.getSession();
            reviews= session
                    .createNamedQuery("get_Review_ByIdItem", Review.class)
                    .setParameter("idItem", idItem)
                    .getResultList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return reviews;
    }

    //HECHO
    @Override
    public List<Review> getReviewsByIdPurchase(int idPurchase) {
        List<Review> reviews = null;
        try {
            session = HibernateUtils.getSession();
            reviews = session
                    .createNamedQuery("get_Review_ByIdPurchase",Review.class)
                    .setParameter("idPurchase", idPurchase)
                    .getResultList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return reviews;
    }

    //HECHO
    @Override
    public List<Review> getReviewsByCustomerId(int idCustomer) {
        List<Review> reviews = null;
        try {
            session = HibernateUtils.getSession();
            reviews= session
                    .createNamedQuery("get_Review_ByIdCustomer",Review.class)
                    .setParameter("idCustomer", idCustomer)
                    .getResultList();

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return reviews;
    }

    //HECHO
    @Override
    public List<Review> getReviewsByItemAndCustomer(int idItem, int idCustomer) {
        List<Review> reviews = null;
        try {
            session = HibernateUtils.getSession();
            reviews = session
                    .createNamedQuery("get_Review_ByIdCustomer_and_IdPurchase",Review.class)
                    .setParameter("idItem", idItem)
                    .setParameter("idCustomer", idCustomer)
                    .getResultList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return reviews;
    }

    @Override
    public List<Review> getReviewsByRate(int rate) {
        List<Review> reviews = null;
        try {
            session = HibernateUtils.getSession();
            reviews = session
                    .createNamedQuery("get_Review_ByRate",Review.class)
                    .setParameter("rate", rate)
                    .getResultList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return reviews;
    }

    @Override
    public double avgRatingByIdItem(int idItem) {
        double avgRating=-1;
        try {
            session = HibernateUtils.getSession();
            List<Double> avg = session
                    .createNamedQuery("avgRating_Review_ByIdItem",Double.class)
                    .setParameter("idItem", idItem)
                    .getResultList();
            if(avg.get(0)!=null){
                avgRating = avg.get(0);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return avgRating;
    }

    @Override
    public List<Review> getReviewsByIdItemOrderByDateAsc(int idItem) {
        List<Review> reviews = null;
        try {
            session = HibernateUtils.getSession();
            reviews = session
                    .createNamedQuery("get_Review_ByIdItem_OrderByDateAsc",Review.class)
                    .setParameter("idItem", idItem)
                    .getResultList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return reviews;
    }

    @Override
    public List<Review> getReviewsByIdItemOrderByDateDesc(int idItem) {
        List<Review> reviews = null;
        try {
            session = HibernateUtils.getSession();
            reviews = session
                    .createNamedQuery("get_Review_ByIdItem_OrderByDateDesc",Review.class)
                    .setParameter("idItem", idItem)
                    .getResultList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return reviews;
    }

    @Override
    public List<Review> getReviewsByIdItemOrderByRateAsc(int idItem) {
        List<Review> reviews = null;
        try {
            session = HibernateUtils.getSession();
            reviews = session
                    .createNamedQuery("get_Review_ByIdItem_OrderByRatingAsc",Review.class)
                    .setParameter("idItem", idItem)
                    .getResultList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return reviews;
    }

    @Override
    public List<Review> getReviewsByIdItemOrderByRateDesc(int idItem) {
        List<Review> reviews = null;
        try {
            session = HibernateUtils.getSession();
            reviews = session
                    .createNamedQuery("get_Review_ByIdItem_OrderByRatingDesc",Review.class)
                    .setParameter("idItem", idItem)
                    .getResultList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return reviews;
    }
}
