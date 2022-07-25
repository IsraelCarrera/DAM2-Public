package dao.spring;

import dao.DAOReviews;
import dao.DBConnPool;
import model.Customer;
import model.Item;
import model.Purchase;
import model.Review;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import utils.Querys;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/* Comprobar que el Username es unico, y luego guardar en la tabla con ID(Del costumer), user y pass.

La fecha del review es la de hoy, no se elige. Y si se hace un update, se actualiza la de hoy */

public class SpringDAOReviews implements DAOReviews {
    @Override
    public Review get(int id) {
        JdbcTemplate bd =new JdbcTemplate(DBConnPool.getInstance().getDataSource());
        List<Review> result = bd.query(Querys.SELECT_reviewsID_QUERY, BeanPropertyRowMapper.newInstance(Review.class),id);
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public List<Review> getAll() {
        JdbcTemplate bd = new JdbcTemplate(DBConnPool.getInstance().getDataSource());
        List<Review> reviews = bd.query(Querys.SELECT_allReviews_QUERY, new ProductMapper());
        return reviews.isEmpty() ? null : reviews;
    }

    @Override
    public int save(Review t) {
        KeyHolder kh = new GeneratedKeyHolder();
        JdbcTemplate insert = new JdbcTemplate(DBConnPool.getInstance().getDataSource());
        insert.update(conn -> {
            PreparedStatement ps = conn
                    .prepareStatement(Querys.INSERT_reviews_QUERY, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, t.getRating());
            ps.setString(2, t.getTitle());
            ps.setString(3, t.getDescription());
            ps.setDate(4, Date.valueOf(t.getDate()));
            ps.setInt(5,t.getPurchase().getIdPurchase());
            return ps;
        }, kh);
        return kh.getKey().intValue();

    }

    @Override
    public boolean update(Review t) {
        JdbcTemplate bd = new JdbcTemplate(DBConnPool.getInstance().getDataSource());
            bd.update(Querys.UPDATE_reviews_QUERY,t.getRating(),t.getTitle(),t.getDescription(), t.getDate(),t.getIdReview());
        return true;
    }

    @Override
    public boolean delete(Review t) {
        JdbcTemplate bd = new JdbcTemplate(DBConnPool.getInstance().getDataSource());
        bd.update(Querys.DELETE_reviews_QUERY,t.getIdReview());
        return true;
    }

    @Override
    public List<Review> getReviewsByItemId(int idItem) {
        JdbcTemplate bd = new JdbcTemplate(DBConnPool.getInstance().getDataSource());
        List<Review> result = bd.query(Querys.SELECT_reviewsIdItem_QUERY, new ProductMapper(), idItem);
        return result.isEmpty() ? null : result;
    }

    @Override
    public List<Review> getReviewsByIdPurchase(int idPurchase) {
        JdbcTemplate bd = new JdbcTemplate(DBConnPool.getInstance().getDataSource());
        List<Review> result = bd.query(Querys.SELECT_reviewsIdPurchase_QUERY, new ProductMapper(), idPurchase);
        return result.isEmpty() ? null : result;
    }

    @Override
    public List<Review> getReviewsByCustomerId(int idCustomer) {
        JdbcTemplate bd = new JdbcTemplate(DBConnPool.getInstance().getDataSource());
        List<Review> result = bd.query(Querys.SELECT_reviewsIdUser_QUERY, new ProductMapper(), idCustomer);
        return result.isEmpty() ? null : result;
    }

    @Override
    public List<Review> getReviewsByItemAndCustomer(int idItem, int idCustomer) {
        JdbcTemplate bd = new JdbcTemplate(DBConnPool.getInstance().getDataSource());
        List<Review> result = bd.query(Querys.SELECT_reviewsIdUserIdCustomer_QUERY, new ProductMapper(), idCustomer, idItem);
        return result.isEmpty() ? null : result;
    }

    @Override
    public int countById(int idItem) {
        return 0;
    }


    private static final class ProductMapper implements RowMapper<Review> {
        @Override
        public Review mapRow(ResultSet rs, int rowNum) throws SQLException {
            Review r = new Review();
            r.setIdReview(rs.getInt("idReview"));
            r.setRating(rs.getInt("rating"));
            r.setTitle(rs.getString("title"));
            r.setDescription(rs.getString("description"));
            r.setDate(new java.sql.Date(rs.getDate("Reviews.date").getTime()).toLocalDate());
            Item i = new Item(rs.getInt("idItem"), rs.getString("Items.name"), rs.getString("company"), rs.getFloat("price"));
            Customer c = new Customer(rs.getInt("idCustomer"), rs.getString("Customers.name"), rs.getString("telephone"), rs.getString("address"));
            Purchase p = new Purchase(rs.getInt("Purchases.idPurchase"), c, i, new java.sql.Date(rs.getDate("Purchases.date").getTime()).toLocalDate());
            r.setPurchase(p);
            return r;
        }
    }
}
