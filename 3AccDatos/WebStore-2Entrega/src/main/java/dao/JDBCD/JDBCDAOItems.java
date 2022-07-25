package dao.JDBCD;

import dao.DAOItems;
import dao.DBConnPool;
import dao.DBConnection;
import model.Item;
import model.Purchase;
import utils.Querys;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JDBCDAOItems implements DAOItems {
    private Statement stmt;
    private Connection con;
    private ResultSet rs;
    private DBConnection db;
    private PreparedStatement pstmt;
    private PreparedStatement pstmt2;

    @Override
    public Item get(int id) {
        Item i = null;
        pstmt = null;
        rs = null;
        db = new DBConnection();
        try {
            con = db.getConnection();
            pstmt = con.prepareStatement(Querys.SELECT_itemID_QUERY);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            i = buildListItem(rs).get(0);
        } catch (SQLException e) {
            Logger.getLogger(JDBCDAOItems.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        } finally {
            db.releaseResource(rs);
            db.releaseResource(pstmt);
            db.closeConnection(con);
        }
        return i;
    }


    @Override
    public List<Item> getAll() {
        List<Item> listItem = null;
        stmt = null;
        rs = null;
        db = new DBConnection();
        try {
            con = db.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(Querys.SELECT_allItems_QUERY);
            listItem = buildListItem(rs);
        } catch (SQLException e) {
            Logger.getLogger(JDBCDAOItems.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        } finally {
            db.releaseResource(rs);
            db.releaseResource(stmt);
            db.closeConnection(con);
        }
        return listItem;
    }

    @Override
    public int save(Item t) {
        pstmt = null;
        db = new DBConnection();
        int auto_id=0;

        try {
            con = db.getConnection();
            pstmt = con.prepareStatement(Querys.INSERT_item_QUERY, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, t.getName());
            pstmt.setString(2, t.getCompany());
            pstmt.setFloat(3, (float) t.getPrice());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if(rs.next()) {
                auto_id = rs.getInt(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(JDBCDAOItems.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        } finally {
            db.releaseResource(pstmt);
            db.closeConnection(con);
        }
        return auto_id;
    }

    @Override
    public void update(Item t) {
        pstmt = null;
        db = new DBConnection();
        try {
            con = db.getConnection();
            pstmt = con.prepareStatement(Querys.UPDATE_item_QUERY);
            pstmt.setString(1, t.getName());
            pstmt.setString(2, t.getCompany());
            pstmt.setFloat(3, (float) t.getPrice());
            pstmt.setInt(4, t.getIdItem());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(JDBCDAOItems.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        } finally {
            db.releaseResource(pstmt);
            db.closeConnection(con);
        }
    }

    @Override
    public boolean delete(Item t, List<Purchase> purchases) {
        boolean isDelete;
        if (purchases.isEmpty()) {
            isDelete = deleteWithoutPurch(t);
        } else {
            isDelete = deleteWithPurchases(t, purchases);
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

    @Override
    public void closeDBPool() {
        DBConnPool.closePool(DBConnPool.getInstance().pool);

    }

    private List<Item> buildListItem(ResultSet rs) throws SQLException {
        List<Item> items = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("idItem");
            String name = rs.getString("name");
            String company = rs.getString("company");
            double price = (double)Math.round(rs.getFloat("price") * 100d) / 100d;
            Item item = new Item(id, name, company, price);
            items.add(item);
        }
        return items;
    }

    private boolean deleteWithoutPurch(Item t) {
        boolean isDelete = false;
        pstmt = null;
        db = new DBConnection();
        try {
            con = db.getConnection();
            pstmt = con.prepareStatement(Querys.DELETE_item_QUERY);
            pstmt.setInt(1, t.getIdItem());
            pstmt.executeUpdate();
            isDelete = true;
        } catch (SQLException e) {
            Logger.getLogger(JDBCDAOItems.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        } finally {
            db.releaseResource(rs);
            db.releaseResource(pstmt);
            db.closeConnection(con);
        }
        return isDelete;
    }

    private boolean deleteWithPurchases(Item t, List<Purchase> purchases) {
        boolean isDelete = false;
        pstmt = null;
        pstmt2 = null;
        db = new DBConnection();
        try {
            con = db.getConnection();
            con.setAutoCommit(false);
            pstmt = con.prepareStatement(Querys.DELETE_purchases_QUERY);
            pstmt2 = con.prepareStatement(Querys.DELETE_item_QUERY);
            for (Purchase p : purchases) {
                pstmt.setInt(1, p.getIdPurchase());
                pstmt.executeUpdate();
                con.commit();
            }
            pstmt2.setInt(1, t.getIdItem());
            pstmt2.executeUpdate();
            con.commit();
            isDelete = true;
        } catch (SQLException e) {
            Logger.getLogger(JDBCDAOItems.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                Logger.getLogger(JDBCDAOItems.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            }
            db.releaseResource(pstmt);
            db.releaseResource(pstmt2);
            db.closeConnection(con);
        }
        return isDelete;
    }
}