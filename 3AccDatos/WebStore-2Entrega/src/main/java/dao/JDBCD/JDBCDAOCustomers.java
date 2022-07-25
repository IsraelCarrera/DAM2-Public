package dao.JDBCD;

import dao.DAOCustomers;
import dao.DBConnection;
import model.Customer;
import model.Item;
import utils.Querys;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JDBCDAOCustomers implements DAOCustomers {
    private Statement stmt;
    private Connection con;
    private ResultSet rs;
    private DBConnection db;
    private PreparedStatement pstmt;
    @Override
    public Customer get(int id) {
        Customer c = null;
        pstmt=null;
        rs = null;
        db = new DBConnection();
        try{
            con = db.getConnection();
            pstmt = con.prepareStatement(Querys.SELECT_customerID_QUERY);
            pstmt.setInt(1,id);
            rs = pstmt.executeQuery();
            c = buildCustomer(rs).get(0);
        } catch (SQLException e) {
            Logger.getLogger(JDBCDAOItems.class.getName()).log(Level.SEVERE,e.getMessage(),e);
        }finally {
            db.releaseResource(rs);
            db.releaseResource(pstmt);
            db.closeConnection(con);
        }
        return c;
    }

    @Override
    public List<Customer> getAll() {
        List<Customer> listCustomers = null;
        stmt = null;
        rs=null;
        db = new DBConnection();
        try{
            con = db.getConnection();
            stmt = con.createStatement();

            rs=stmt.executeQuery(Querys.SELECT_allCustomers_QUERY);
            listCustomers= buildCustomer(rs);
        }catch (SQLException e){
            Logger.getLogger(JDBCDAOItems.class.getName()).log(Level.SEVERE,e.getMessage(),e);
        } finally {
            db.releaseResource(rs);
            db.releaseResource(stmt);
            db.closeConnection(con);
        }

        return listCustomers;
    }

    @Override
    public boolean save(Customer t) {
        boolean isSave = false;
        pstmt = null;
        rs = null;
        db = new DBConnection();
        try {
            con = db.getConnection();
            pstmt = con.prepareStatement(Querys.INSERT_costumer_QUERY);
            pstmt.setInt(1,t.getIdCustomer());
            pstmt.setString(2,t.getName());
            pstmt.setString(3,t.getPhone());
            pstmt.setString(4, t.getAddress());
            pstmt.executeUpdate();
            isSave=true;
        } catch (SQLException e) {
            Logger.getLogger(JDBCDAOItems.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        } finally {
            db.releaseResource(rs);
            db.releaseResource(pstmt);
            db.closeConnection(con);
        }
        return isSave;
    }

    @Override
    public void update(Customer t) {
        pstmt = null;
        db = new DBConnection();
        try{
            con = db.getConnection();
            pstmt=con.prepareStatement(Querys.UPDATE_customer_QUERY);
            pstmt.setString(1,t.getName());
            pstmt.setString(2,t.getPhone());
            pstmt.setString(3,  t.getAddress());
            pstmt.setInt(4,t.getIdCustomer());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(JDBCDAOItems.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }finally {
            db.releaseResource(pstmt);
            db.closeConnection(con);
        }
    }

    @Override
    public boolean delete(Customer t) {
        boolean isdelete = false;
        pstmt=null;
        db = new DBConnection();
        try{
            con = db.getConnection();
            pstmt = con.prepareStatement(Querys.DELETE_Customer_QUERY);
            pstmt.setInt(1,t.getIdCustomer());
            pstmt.executeUpdate();
            isdelete=true;
        } catch (SQLException e) {
            Logger.getLogger(JDBCDAOItems.class.getName()).log(Level.SEVERE,e.getMessage(),e);
        }finally {
            db.releaseResource(pstmt);
            db.closeConnection(con);
        }
        return isdelete;
    }

    private List<Customer> buildCustomer(ResultSet rs) throws SQLException{
        List<Customer> customers = new ArrayList<>();
        while(rs.next()){
            int id = rs.getInt("idCustomer");
            String name = rs.getString("name");
            String telephone = rs.getString("telephone");
            String address = rs.getString("address");
            Customer c = new Customer(id,name,telephone,address);
            customers.add(c);
        }
        return customers;
    }
}
