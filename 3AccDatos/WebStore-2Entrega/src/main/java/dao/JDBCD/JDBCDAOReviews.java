package dao.JDBCD;

import dao.DAOReviews;
import dao.DBConnection;
import model.Customer;
import model.Item;
import model.Purchase;
import model.Review;
import utils.Querys;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JDBCDAOReviews implements DAOReviews {
    private Statement stmt;
    private Connection con;
    private ResultSet rs;
    private DBConnection db;
    private PreparedStatement pstmt;

    @Override
    public Review get(int id) {
        return null;
    }

    @Override
    public List<Review> getAll() {
        List<Review> listReviews = null;
        stmt = null;
        rs = null;
        db = new DBConnection();
        try {
            con = db.getConnection();
            stmt = con.createStatement();

            rs = stmt.executeQuery(Querys.SELECT_allReviews_QUERY);
            listReviews = builReviews(rs);
        } catch (SQLException e) {
            Logger.getLogger(JDBCDAOItems.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        } finally {
            db.releaseResource(rs);
            db.releaseResource(stmt);
            db.closeConnection(con);
        }
        return listReviews;
    }

    @Override
    public void save(Review t) {

    }

    @Override
    public void update(Review t) {

    }

    @Override
    public void delete(Review t) {

    }

    @Override
    public List<Review> getReviewsByItemId(int idItem) {
        List<Review> reviews = null;
        pstmt = null;
        rs = null;
        db = new DBConnection();
        try {
            con = db.getConnection();
            pstmt = con.prepareStatement(Querys.SELECT_reviewsIdItem_QUERY);
            pstmt.setInt(1, idItem);
            rs = pstmt.executeQuery();
            reviews = builReviews(rs);
        } catch (SQLException e) {
            Logger.getLogger(JDBCDAOItems.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        } finally {
            db.releaseResource(rs);
            db.releaseResource(pstmt);
            db.closeConnection(con);
        }
        return reviews;
    }

    @Override
    public List<Review> getReviewsByIdPurchase(int idPurchase) {
        List<Review> reviews = null;
        pstmt = null;
        rs = null;
        db = new DBConnection();
        try {
            con = db.getConnection();
            pstmt = con.prepareStatement(Querys.SELECT_reviewsIdPurchase_QUERY);
            pstmt.setInt(1, idPurchase);
            rs = pstmt.executeQuery();
            reviews = builReviews(rs);
        } catch (SQLException e) {
            Logger.getLogger(JDBCDAOItems.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        } finally {
            db.releaseResource(rs);
            db.releaseResource(pstmt);
            db.closeConnection(con);
        }
        return reviews;
    }

    private List<Review> builReviews(ResultSet rs) throws SQLException {
        List<Review> reviews = new ArrayList<>();
        while (rs.next()) {
            int idReview = rs.getInt("idReview");
            int rating = rs.getInt("rating");
            String title = rs.getString("title");
            String desc = rs.getString("description");
            LocalDate date = new java.sql.Date(rs.getDate("Reviews.date").getTime()).toLocalDate();
            Item i = new Item(rs.getInt("idItem"), rs.getString("Items.name"), rs.getString("company"), rs.getFloat("price"));
            Customer c = new Customer(rs.getInt("idCustomer"), rs.getString("Customers.name"), rs.getString("telephone"), rs.getString("address"));
            Purchase p = new Purchase(rs.getInt("Purchases.idPurchase"), c, i, new java.sql.Date(rs.getDate("Purchases.date").getTime()).toLocalDate());
            Review r = new Review(idReview, rating, title, desc, date, p);
            reviews.add(r);
        }

        return reviews;
    }
}
