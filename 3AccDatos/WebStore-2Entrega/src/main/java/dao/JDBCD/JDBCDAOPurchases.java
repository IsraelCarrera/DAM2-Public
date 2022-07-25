package dao.JDBCD;

import dao.DAOPurchases;
import dao.DBConnection;
import model.Customer;
import model.Item;
import model.Purchase;
import utils.Querys;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JDBCDAOPurchases implements DAOPurchases {
    private Statement stmt;
    private Connection con;
    private ResultSet rs;
    private DBConnection db;
    private PreparedStatement pstmt;

    @Override
    public Purchase get(int id) {
        Purchase p = null;
        pstmt=null;
        rs = null;
        db = new DBConnection();
        try{
            con = db.getConnection();
            pstmt = con.prepareStatement(Querys.SELECT_purchasesID_QUERY);
            pstmt.setInt(1,id);
            rs = pstmt.executeQuery();
            p = buildPurchases(rs).get(0);
        } catch (SQLException e) {
            Logger.getLogger(JDBCDAOItems.class.getName()).log(Level.SEVERE,e.getMessage(),e);
        }finally {
            db.releaseResource(rs);
            db.releaseResource(pstmt);
            db.closeConnection(con);
        }
        return p;
    }

    @Override
    public List<Purchase> getAll(){
        List<Purchase> listPurchases= null;
        stmt = null;
        rs=null;
        db = new DBConnection();
        try{
            con = db.getConnection();
            stmt = con.createStatement();
            rs=stmt.executeQuery(Querys.SELECT_allPurchases_QUERY);
            listPurchases= buildPurchases(rs);
        }catch (SQLException e){
            Logger.getLogger(JDBCDAOItems.class.getName()).log(Level.SEVERE,e.getMessage(),e);
        } finally {
            db.releaseResource(rs);
            db.releaseResource(stmt);
            db.closeConnection(con);
        }
        return listPurchases;
    }


    @Override
    public boolean save(Purchase t) {
        boolean isDone = false;
        pstmt = null;
        db = new DBConnection();
        try {
            con = db.getConnection();
            pstmt = con.prepareStatement(Querys.INSERT_purchases_QUERY);
            pstmt.setInt(1,t.getIdPurchase());
            pstmt.setDate(2, Date.valueOf(t.getDate()));
            pstmt.setInt(3,t.getCustomer().getIdCustomer());
            pstmt.setInt(4, t.getItem().getIdItem());
            pstmt.executeUpdate();
            isDone=true;
        } catch (SQLException e) {
            Logger.getLogger(JDBCDAOItems.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        } finally {
            db.releaseResource(pstmt);
            db.closeConnection(con);
        }
        return isDone;
    }

    @Override
    public void update(Purchase t) {pstmt = null;
        db = new DBConnection();
        try{
            con = db.getConnection();
            pstmt=con.prepareStatement(Querys.UPDATE_purchase_QUERY);
            pstmt.setDate(1,Date.valueOf(t.getDate()));
            pstmt.setInt(2,t.getIdPurchase());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(JDBCDAOItems.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }finally {
            db.releaseResource(pstmt);
            db.closeConnection(con);
        }

    }

    @Override
    public boolean delete(Purchase t) {
        boolean isDelete = false;
        stmt = null;
        pstmt=null;
        db = new DBConnection();
        try{
            con = db.getConnection();
            pstmt = con.prepareStatement(Querys.DELETE_purchases_QUERY);
            pstmt.setInt(1,t.getIdPurchase());
            pstmt.executeUpdate();
            isDelete=true;
        } catch (SQLException e) {
            Logger.getLogger(JDBCDAOItems.class.getName()).log(Level.SEVERE,e.getMessage(),e);
        }finally {
            db.releaseResource(pstmt);
            db.closeConnection(con);
        }
        return isDelete;
    }

    @Override
    public List<Purchase> checkPurchaseByIdItem(int idItem) {
        List<Purchase> listPurchases = null;
        pstmt=null;
        rs = null;
        db = new DBConnection();
        try{
            con = db.getConnection();
            pstmt = con.prepareStatement(Querys.SELECT_purchasesIdItem_QUERY);
            pstmt.setInt(1,idItem);
            rs = pstmt.executeQuery();
            listPurchases = buildPurchases(rs);
        } catch (SQLException e) {
            Logger.getLogger(JDBCDAOItems.class.getName()).log(Level.SEVERE,e.getMessage(),e);
        }finally {
            db.releaseResource(rs);
            db.releaseResource(pstmt);
            db.closeConnection(con);
        }
        return listPurchases;
    }

    @Override
    public List<Purchase> getPurchasesByDate(LocalDate date) {
        List<Purchase> listPurchases = null;
        pstmt=null;
        rs = null;
        db = new DBConnection();
        try{
            con = db.getConnection();
            pstmt = con.prepareStatement(Querys.SELECT_purchasesDate_QUERY);
            pstmt.setDate(1,Date.valueOf(date));
            rs = pstmt.executeQuery();
            listPurchases = buildPurchases(rs);
        } catch (SQLException e) {
            Logger.getLogger(JDBCDAOItems.class.getName()).log(Level.SEVERE,e.getMessage(),e);
        }finally {
            db.releaseResource(rs);
            db.releaseResource(pstmt);
            db.closeConnection(con);
        }
        return listPurchases;
    }

    @Override
    public List<Purchase> getPurchasesByIdCostumer(int idCostumer) {
        List<Purchase> purchaseList = null;
        pstmt = null;
        rs = null;
        db = new DBConnection();
        try {
            con = db.getConnection();
            pstmt = con.prepareStatement(Querys.SELECT_purchasesIdCostumer_QUERY);
            pstmt.setInt(1, idCostumer);
            rs = pstmt.executeQuery();
            purchaseList = buildPurchases(rs);
        } catch (SQLException e) {
            Logger.getLogger(JDBCDAOItems.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        } finally {
            db.releaseResource(rs);
            db.releaseResource(pstmt);
            db.closeConnection(con);
        }
        return purchaseList;
    }

    private List<Purchase> buildPurchases(ResultSet rs) throws SQLException {
        List<Purchase> purchases = new ArrayList<>();
        while(rs.next()){
            int id = rs.getInt("idPurchase");
            LocalDate date = new java.sql.Date(rs.getDate("date").getTime()).toLocalDate();
            //Qué hacer con IDCustomer e IDItem. Se tendrán que pillar de un select * from customer where idCustomer= .getInt("idCustomer")
            //Customers: idCustomer Customer.name, telephone, address
            Customer c = new Customer(rs.getInt("Customers.idCustomer"),rs.getString("Customers.name"),rs.getString("telephone"),rs.getString("address"));
            //Items: idItem, Item.name, company, price
            Item i = new Item(rs.getInt("Items.idItem"),rs.getString("Items.name"),rs.getString("company"),rs.getFloat("price"));
            Purchase p = new Purchase(id,c,i,date);
            purchases.add(p);

        }
        return purchases;
    }
}
